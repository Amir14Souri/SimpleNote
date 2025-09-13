package ir.sharif.simplenote.domain.repository

import androidx.paging.PagingData
import ir.sharif.simplenote.database.Notes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNote(name: String, content: String)

    suspend fun getNoteById(id: Long): ir.sharif.simplenote.domain.model.Note?

    suspend fun updateNoteById(id: Long,name: String, content: String)

    suspend fun deleteNoteById(id: Long): Result<Unit>

    fun getNotesPaged(): Flow<PagingData<Notes>>
    fun searchNotesPaged(query: String): Flow<PagingData<Notes>>

    suspend fun sync(): Result<Unit>


    suspend fun syncAll(): Result<Unit>

    fun schedulePeriodicSync()

    fun scheduleImmediateSync()
}