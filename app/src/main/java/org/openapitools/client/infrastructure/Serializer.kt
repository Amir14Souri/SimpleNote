package ir.sharif.simplenote.client.infrastructure

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID

object Serializer {
    @JvmStatic
    val gsonBuilder: GsonBuilder = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeAdapter(ByteArray::class.java, ByteArrayAdapter())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ChangePasswordRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error401.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error403.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error404.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error405.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error406.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error415.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error500.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse401.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse403.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse404.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse405.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse406.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse415.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse500.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Message.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Note.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NoteRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateErrorResponse400.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationItemError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PaginatedNoteList.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseError.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseErrorResponse.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PatchedNoteRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Register.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.RegisterRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPair.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPairRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefresh.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefreshRequest.CustomTypeAdapterFactory())
        .registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.UserInfo.CustomTypeAdapterFactory())

    @JvmStatic
    val gson: Gson by lazy {
        gsonBuilder.create()
    }
}
