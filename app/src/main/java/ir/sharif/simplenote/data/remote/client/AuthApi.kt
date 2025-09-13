package ir.sharif.simplenote.data.remote.client

import ir.sharif.simplenote.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.AuthRegisterCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.AuthTokenCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.ChangePasswordRequest
import ir.sharif.simplenote.data.remote.model.ErrorResponse401
import ir.sharif.simplenote.data.remote.model.ErrorResponse404
import ir.sharif.simplenote.data.remote.model.ErrorResponse405
import ir.sharif.simplenote.data.remote.model.ErrorResponse406
import ir.sharif.simplenote.data.remote.model.ErrorResponse415
import ir.sharif.simplenote.data.remote.model.ErrorResponse500
import ir.sharif.simplenote.data.remote.model.Message
import ir.sharif.simplenote.data.remote.model.ParseErrorResponse
import ir.sharif.simplenote.data.remote.model.Register
import ir.sharif.simplenote.data.remote.model.RegisterRequest
import ir.sharif.simplenote.data.remote.model.TokenObtainPair
import ir.sharif.simplenote.data.remote.model.TokenObtainPairRequest
import ir.sharif.simplenote.data.remote.model.TokenRefresh
import ir.sharif.simplenote.data.remote.model.TokenRefreshRequest
import ir.sharif.simplenote.data.remote.model.UserInfo

interface AuthApi {
    @POST("api/auth/change-password/")
    suspend fun authChangePasswordCreate(@Body changePasswordRequest: ChangePasswordRequest): Response<Message>

    @POST("api/auth/register/")
    suspend fun authRegisterCreate(@Body registerRequest: RegisterRequest): Response<Register>

    @POST("api/auth/token/")
    suspend fun authTokenCreate(@Body tokenObtainPairRequest: TokenObtainPairRequest): Response<TokenObtainPair>

    @POST("api/auth/token/refresh/")
    suspend fun authTokenRefreshCreate(@Body tokenRefreshRequest: TokenRefreshRequest): Response<TokenRefresh>

    @GET("api/auth/userinfo/")
    suspend fun authUserinfoRetrieve(): Response<UserInfo>
}
