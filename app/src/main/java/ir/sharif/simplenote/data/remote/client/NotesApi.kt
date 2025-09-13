package ir.sharif.simplenote.data.remote.client

import ir.sharif.simplenote.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import ir.sharif.simplenote.data.remote.model.ErrorResponse401
import ir.sharif.simplenote.data.remote.model.ErrorResponse403
import ir.sharif.simplenote.data.remote.model.ErrorResponse404
import ir.sharif.simplenote.data.remote.model.ErrorResponse405
import ir.sharif.simplenote.data.remote.model.ErrorResponse406
import ir.sharif.simplenote.data.remote.model.ErrorResponse415
import ir.sharif.simplenote.data.remote.model.ErrorResponse500
import ir.sharif.simplenote.data.remote.model.Note
import ir.sharif.simplenote.data.remote.model.NoteRequest
import ir.sharif.simplenote.data.remote.model.NotesBulkCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.NotesCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.NotesFilterListErrorResponse400
import ir.sharif.simplenote.data.remote.model.NotesPartialUpdateErrorResponse400
import ir.sharif.simplenote.data.remote.model.NotesUpdateErrorResponse400
import ir.sharif.simplenote.data.remote.model.PaginatedNoteList
import ir.sharif.simplenote.data.remote.model.ParseErrorResponse
import ir.sharif.simplenote.data.remote.model.PatchedNoteRequest

interface NotesApi {
    @POST("api/notes/bulk")
    suspend fun notesBulkCreate(@Body noteRequest: kotlin.collections.List<NoteRequest>): Response<kotlin.collections.List<Note>>

    @POST("api/notes/")
    suspend fun notesCreate(@Body noteRequest: NoteRequest): Response<Note>

    @DELETE("api/notes/{id}/")
    suspend fun notesDestroy(@Path("id") id: kotlin.Int): Response<Unit>

    @GET("api/notes/filter")
    suspend fun notesFilterList(@Query("description") description: kotlin.String? = null, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.Int? = null, @Query("title") title: kotlin.String? = null, @Query("updated__gte") updatedGte: java.time.OffsetDateTime? = null, @Query("updated__lte") updatedLte: java.time.OffsetDateTime? = null): Response<PaginatedNoteList>

    @GET("api/notes/")
    suspend fun notesList(@Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.Int? = null): Response<PaginatedNoteList>

    @PATCH("api/notes/{id}/")
    suspend fun notesPartialUpdate(@Path("id") id: kotlin.Int, @Body patchedNoteRequest: PatchedNoteRequest? = null): Response<Note>

    @GET("api/notes/{id}/")
    suspend fun notesRetrieve(@Path("id") id: kotlin.Int): Response<Note>

    @PUT("api/notes/{id}/")
    suspend fun notesUpdate(@Path("id") id: kotlin.Int, @Body noteRequest: NoteRequest): Response<Note>
}
