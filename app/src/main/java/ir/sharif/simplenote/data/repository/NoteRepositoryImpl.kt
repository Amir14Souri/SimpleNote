@file:OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)

package ir.sharif.simplenote.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ir.sharif.simplenote.data.local.SyncManager
import ir.sharif.simplenote.data.network.NetworkConnectivityManager
import ir.sharif.simplenote.data.remote.client.NotesApi
import ir.sharif.simplenote.data.remote.model.NoteRequest
import ir.sharif.simplenote.data.remote.model.NotesFilterListErrorResponse400
import ir.sharif.simplenote.data.worker.SyncWorker
import ir.sharif.simplenote.database.Notes
import ir.sharif.simplenote.database.NotesQueries
import ir.sharif.simplenote.domain.model.Note
import ir.sharif.simplenote.domain.model.toNote
import ir.sharif.simplenote.domain.repository.NoteRepository
import ir.sharif.simplenote.paging.CursorBasedNotesPagingSource
import ir.sharif.simplenote.paging.CursorBasedSearchNotesPagingSource
import ir.sharif.simplenote.util.safeApiCall
import ir.sharif.simplenote.util.safeApiCallResponse
import ir.sharif.simplenote.util.toResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val notesQueries: NotesQueries,
    private val notesApi: NotesApi,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val syncManager: SyncManager,
    private val workManager: WorkManager,
) : NoteRepository {

    private val logger = Logger.getLogger("note_repository")

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun createNote(name: String, content: String) = withContext(dispatcher) {
        withRefresh {
            val nowLocalTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            if (networkConnectivityManager.isConnected.value) {
                val resp = safeApiCall {
                    notesApi.notesCreate(
                        noteRequest = NoteRequest(
                            title = name,
                            description = content
                        )
                    )
                }
                resp.onSuccess {
                    notesQueries.createNoteFromAPI(
                        api_id = it.id.toLong(),
                        title = it.title,
                        content = it.description,
                        created_at = it.createdAt.toLocalDateTime().toKotlinLocalDateTime(),
                        updated_at = it.updatedAt.toLocalDateTime().toKotlinLocalDateTime(),
                    )
                    return@withRefresh
                }
            }
            notesQueries.createLocalNote(name, content, nowLocalTime, nowLocalTime)
        }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(dispatcher) {
        notesQueries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun updateNoteById(id: Long, name: String, content: String) =
        withContext(dispatcher) {
            withRefresh {
                val nowLocalTime =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                if (networkConnectivityManager.isConnected.value) {
                    val note = notesQueries.getNoteById(id).executeAsOne()
                    if (note.api_id == null) {
                        safeApiCall {
                            notesApi.notesCreate(
                                noteRequest = NoteRequest(
                                    title = name,
                                    description = content,
                                )
                            )
                        }.onSuccess {
                            notesQueries.transaction {
                                notesQueries.setApiId(id = id, api_id = it.id.toLong())
                                notesQueries.updateNoteById(
                                    id = id, title = name, content = content,
                                    updated_at = Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                                    is_synced = true
                                )
                            }
                        }
                    } else {
                        safeApiCall {
                            notesApi.notesUpdate(
                                id = note.api_id.toInt(),
                                noteRequest = NoteRequest(
                                    title = name,
                                    description = content
                                )
                            )
                        }.onSuccess {
                            notesQueries.updateNoteById(
                                name, content, nowLocalTime,
                                true, id
                            )
                        }
                    }

                }
                notesQueries.updateNoteById(
                    id = id, title = name, content = content,
                    updated_at = nowLocalTime, is_synced = false
                )
                return@withRefresh
            }
        }

    override suspend fun deleteNoteById(id: Long): Result<Unit> = withContext(dispatcher) {
        withRefresh {
            if (networkConnectivityManager.isConnected.value) {
                safeApiCall { notesApi.notesDestroy(id.toInt()) }.onSuccess {
                    notesQueries.deleteNoteById(id)
                }
            }
            try {
                notesQueries.softDeleteNote(id)
            } catch (e: Exception) {
                return@withRefresh Result.failure(e)
            }

            return@withRefresh Result.success(Unit)
        }
    }

    override fun getNotesPaged(): Flow<PagingData<Notes>> {
        return refreshTrigger
            .onStart { emit(Unit) }
            .flatMapLatest {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                        prefetchDistance = 5,
                        initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        CursorBasedNotesPagingSource(notesQueries)
                    }
                ).flow
            }
    }

    override fun searchNotesPaged(query: String): Flow<PagingData<Notes>> {
        return refreshTrigger
            .onStart { emit(Unit) }
            .flatMapLatest {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                        prefetchDistance = 5,
                        initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        CursorBasedSearchNotesPagingSource(query, notesQueries)
                    }
                ).flow
            }
    }

    override suspend fun syncAll(): Result<Unit> = withContext(dispatcher) {
        var page = 1
        loop@ while (true) {
            val pageResp = safeApiCallResponse { notesApi.notesFilterList(page = page) }.getOrElse {
                return@withContext Result.failure(it)
            }
            val res = pageResp.toResource<_, NotesFilterListErrorResponse400>()

            res.onSuccess { notes ->
                notesQueries.transaction {
                    notes.results.forEach { note ->
                        notesQueries.createNoteFromAPI(
                            api_id = note.id.toLong(),
                            title = note.title,
                            content = note.description,
                            created_at = note.createdAt.toLocalDateTime().toKotlinLocalDateTime(),
                            updated_at = note.updatedAt.toLocalDateTime().toKotlinLocalDateTime()
                        )
                    }
                }

                if (notes.next == null) {
                    break@loop
                }
                page++
            }.onAnyError { _, _, it ->
                return@withContext Result.failure(
                    it ?: Exception("unknown error")
                )
            }
        }

        return@withContext Result.success(Unit)
    }

    suspend fun syncStales() = withContext(dispatcher) {
        logger.info("started syncing stales")
        val stales = notesQueries.getStaleNotes().executeAsList()

        val toBeCreated = stales.filter { it.api_id == null && it.is_deleted == false }
        val toBeUpdated = stales.filter { it.api_id != null && it.is_deleted == false }
        val toBeDeleted = stales.filter { it.api_id != null && it.is_deleted == true }

        safeApiCall {
            notesApi.notesBulkCreate(
                noteRequest = toBeCreated.map { NoteRequest(it.title, it.content) }
            )
        }.onFailure { _ -> return@withContext }.onSuccess { notesResp ->
            notesQueries.transaction {
                toBeCreated.zip(notesResp).forEach { (note, resp) ->
                    {
                        notesQueries.setApiId(id = note.id, api_id = resp.id.toLong())
                    }
                }
            }
        }

        toBeUpdated.forEach { note ->
            safeApiCall {
                notesApi.notesUpdate(
                    note.api_id!!.toInt(),
                    NoteRequest(note.title, note.content)
                )
            }.onSuccess {
                notesQueries.markSynced(note.id)
            }
        }

        toBeDeleted.forEach {
            note ->
                safeApiCall {
                    notesApi.notesDestroy(
                        note.api_id!!.toInt(),
                    )
                }.onSuccess {
                    notesQueries.deleteNoteById(note.id)
                }
        }
    }

    override suspend fun sync(): Result<Unit> = withContext(dispatcher) {
        val lastSync = syncManager.getLastSync()?.let { it ->
            OffsetDateTime.ofInstant(
                it.toJavaInstant(),
                ZoneId.systemDefault()
            )
        }

        var page = 1
        while (true) {
            val pageResp = safeApiCall {
                notesApi.notesFilterList(page = page, updatedGte = lastSync)
            }.getOrElse {
                return@withContext Result.failure(Exception(it))
            }
            pageResp.results.forEach {
                val note = notesQueries.getNoteByApiId(it.id.toLong()).executeAsOneOrNull()
                if (note == null) {
                    notesQueries.createNoteFromAPI(
                        title = it.title,
                        content = it.description,
                        created_at = it.createdAt.toLocalDateTime().toKotlinLocalDateTime(),
                        updated_at = it.updatedAt.toLocalDateTime().toKotlinLocalDateTime(),
                        api_id = it.id.toLong()
                    )
                } else {
                    val remoteUpdatedAt = it.updatedAt.toLocalDateTime().toKotlinLocalDateTime()

                    val remoteInstant = remoteUpdatedAt.toInstant(TimeZone.UTC)
                    val localInstant = note.updated_at.toInstant(TimeZone.UTC)


                    if (remoteInstant - localInstant >  5.seconds) {
                        notesQueries.updateNoteById(
                            id = note.id,
                            title = it.title,
                            content = it.description,
                            updated_at = remoteUpdatedAt,
                            is_synced = true
                        )
                    }
                }
            }

            if (pageResp.next == null) {
                break
            }
            page++
        }
        syncStales()
        syncManager.setLastSyncNow()
        return@withContext Result.success(Unit)
    }


    override fun schedulePeriodicSync() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).setInitialDelay(0, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "periodic_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    override fun scheduleImmediateSync() {
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueue(syncRequest)
    }


    private suspend fun <T> withRefresh(block: suspend () -> T): T {
        val res = block()
        refreshTrigger.emit(Unit)
        return res
    }

}