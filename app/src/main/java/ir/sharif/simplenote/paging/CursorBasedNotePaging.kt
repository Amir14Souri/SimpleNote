package ir.sharif.simplenote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.sharif.simplenote.database.Notes
import ir.sharif.simplenote.database.NotesQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

class CursorBasedNotesPagingSource(
    private val queries: NotesQueries
) : PagingSource<CursorBasedNotesPagingSource.CursorKey, Notes>() {

    data class CursorKey(
        val createdAt: LocalDateTime,
        val id: Long
    )

    override suspend fun load(params: LoadParams<CursorKey>): LoadResult<CursorKey, Notes> {
        return try {
            withContext(Dispatchers.IO) {
                val pageSize = params.loadSize

                val notes = when (params) {
                    is LoadParams.Refresh -> {
                        queries.getNotesPaginatedInitial(pageSize.toLong()).executeAsList()
                    }
                    is LoadParams.Append -> {
                        val cursor = params.key
                        queries.getNotesPaginated(
                            cursor.createdAt,
                            cursor.id,
                            pageSize.toLong()
                        ).executeAsList()
                    }
                    is LoadParams.Prepend -> emptyList()
                }

                val nextKey = if (notes.isEmpty()) {
                    null
                } else {
                    val lastNote = notes.last()
                    CursorKey(lastNote.created_at, lastNote.id)
                }

                LoadResult.Page(
                    data = notes,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<CursorKey, Notes>): CursorKey? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.let { note ->
                CursorKey(note.created_at, note.id)
            }
        }
    }
}
