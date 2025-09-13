@file:OptIn(ExperimentalTime::class)

package ir.sharif.simplenote.data.repository

import ir.sharif.simplenote.data.local.SessionManager
import ir.sharif.simplenote.data.network.NetworkConnectivityManager
import ir.sharif.simplenote.data.remote.client.AuthApi
import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationError
import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationItemErrorAttrEnum
import ir.sharif.simplenote.data.remote.model.AuthRegisterCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationError
import ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationItemErrorAttrEnum
import ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationError
import ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationItemErrorAttrEnum
import ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateErrorResponse400
import ir.sharif.simplenote.data.remote.model.ChangePasswordRequest
import ir.sharif.simplenote.data.remote.model.ErrorResponse401
import ir.sharif.simplenote.data.remote.model.ParseErrorResponse
import ir.sharif.simplenote.data.remote.model.RegisterRequest
import ir.sharif.simplenote.data.remote.model.TokenObtainPair
import ir.sharif.simplenote.data.remote.model.TokenObtainPairRequest
import ir.sharif.simplenote.data.remote.model.TokenRefreshRequest
import ir.sharif.simplenote.domain.model.ChangePasswordError
import ir.sharif.simplenote.domain.model.ChangePasswordValidationError
import ir.sharif.simplenote.database.NotesQueries
import ir.sharif.simplenote.domain.model.LoginError
import ir.sharif.simplenote.domain.model.LoginValidationError
import ir.sharif.simplenote.domain.model.RegisterError
import ir.sharif.simplenote.domain.model.RegisterError.*
import ir.sharif.simplenote.domain.model.RegisterValidationError.*
import ir.sharif.simplenote.domain.model.Token
import ir.sharif.simplenote.domain.model.User
import ir.sharif.simplenote.domain.repository.UserRepository
import ir.sharif.simplenote.util.Resource
import ir.sharif.simplenote.util.Resource.*
import ir.sharif.simplenote.util.safeApiCall
import ir.sharif.simplenote.util.safeApiCallResponse
import ir.sharif.simplenote.util.toResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager,
    private val notesQueries: NotesQueries,
    private val networkConnectivityManager: NetworkConnectivityManager,
) : UserRepository {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ): Resource<Unit, RegisterError> = withContext(dispatcher) {
            val resp = safeApiCallResponse {
                authApi.authRegisterCreate(RegisterRequest(username, password, email, firstName, lastName))
            }.getOrElse {
                return@withContext Failure("could not register", throwable = it)
            }

            val res = resp.toResource<_, AuthRegisterCreateErrorResponse400>()
            when (res) {
                is Success -> {
                    Success(Unit)
                }
                is Failure -> {
                    Failure(message = res.message)
                }
                is Error -> {
                    when (val errRespInstance = res.error.actualInstance) {
                        is AuthRegisterCreateValidationError ->
                            Error(
                                ValidationError(
                                    errors = errRespInstance.errors.map { error ->
                                        when (error.attr) {
                                            AuthRegisterCreateValidationItemErrorAttrEnum.PASSWORD ->
                                                Password(error.detail)

                                            AuthRegisterCreateValidationItemErrorAttrEnum.EMAIL ->
                                                Email(error.detail)

                                            AuthRegisterCreateValidationItemErrorAttrEnum.USERNAME ->
                                                Username(error.detail)

                                            AuthRegisterCreateValidationItemErrorAttrEnum.FIRST_NAME ->
                                                FirstName(error.detail)

                                            AuthRegisterCreateValidationItemErrorAttrEnum.LAST_NAME ->
                                                LastName(error.detail)

                                            AuthRegisterCreateValidationItemErrorAttrEnum.NON_FIELD_ERRORS ->
                                                NonField(error.detail)
                                        }
                                    }
                                )
                            )

                        is ParseErrorResponse -> Error(
                            ClientError(
                                errRespInstance.errors.joinToString("\n") { err ->
                                    "${err.attr}: ${err.detail}"
                                }
                            )
                        )
                        else -> Failure(message = "unknown error")
                    }
                }
            }
    }

    override suspend fun login(username: String, password: String): Resource<User, LoginError> = withContext(dispatcher) {
        val resp = safeApiCallResponse {
            authApi.authTokenCreate(TokenObtainPairRequest(username, password))
        }.getOrElse {
             return@withContext Failure("could not login", throwable = it)
        }

        if (resp.code() == 400) {
            handleLogin400Error(resp)
        }
        val res = resp.toResource<_, ErrorResponse401>()

        when (res) {
            is Failure -> {
                Failure(message = res.message)
            }

            is Error -> {
                Error(LoginError.AuthError(res.error.errors.first().code))
            }
            is Success -> {
                val nowLocalDateTime: LocalDateTime = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())

                sessionManager.saveToken(
                    Token(
                        res.data.access,
                        res.data.refresh,
                        nowLocalDateTime
                    )
                )

                val profileUser = getProfile().getOrElse {
                    return@withContext Failure("could not retrieve profile")
                }

                sessionManager.saveUserProfile(profileUser)
                Success(profileUser)
            }
        }
    }

    override suspend fun changePassword(
        oldPassword: String, newPassword: String
    ): Resource<Unit, ChangePasswordError> = withContext(dispatcher) {
        val resp = safeApiCallResponse {
            authApi.authChangePasswordCreate(ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword
            ))
        }.getOrElse {
            return@withContext Failure("could not change password", throwable = it)
        }

        val res = resp.toResource<_, AuthChangePasswordCreateErrorResponse400>()

        when (res) {
            is Failure -> Failure(message = res.message)
            is Error -> when(val errRespInstance = res.error.actualInstance) {
                is AuthChangePasswordCreateValidationError -> Error(
                    ChangePasswordError.ValidationError(
                        errors = errRespInstance.errors.map { error ->
                            when (error.attr) {
                                AuthChangePasswordCreateValidationItemErrorAttrEnum.OLD_PASSWORD ->
                                    ChangePasswordValidationError.OldPassword(error.detail)
                                AuthChangePasswordCreateValidationItemErrorAttrEnum.NEW_PASSWORD ->
                                    ChangePasswordValidationError.NewPassword(error.detail)
                                AuthChangePasswordCreateValidationItemErrorAttrEnum.NON_FIELD_ERRORS ->
                                    ChangePasswordValidationError.NoneField(error.detail)
                            }
                        }
                    )
                )
                else -> Failure(message = "unknown error")
            }
            is Success -> Success(Unit)
        }
    }

    override suspend fun refreshToken(): Resource<String, String> = withContext(dispatcher) {
        val refreshToken = sessionManager.getRefreshToken().orEmpty()
        val resp = safeApiCallResponse( {
            authApi.authTokenRefreshCreate(TokenRefreshRequest(refreshToken))
        }).getOrElse {
            return@withContext Failure("could not refresh token", throwable = it)
        }
        val res = resp.toResource<_, AuthTokenRefreshCreateErrorResponse400>()

        when (res) {
            is Success -> {
                val nowLocalDateTime: LocalDateTime = Clock.System.now().plus(30.minutes)
                    .toLocalDateTime(TimeZone.currentSystemDefault())

                sessionManager.saveToken(
                    Token(
                        res.data.access,
                        refreshToken,
                        nowLocalDateTime
                    )
                )
                Success(res.data.access)
            }
            is Error -> {
                Error(res.error.toString())
            }
            is Failure -> {
                Error(res.message.orEmpty())
            }
        }
    }

    private suspend fun getProfile(): Result<User> = withContext(dispatcher) {
        safeApiCall {  authApi.authUserinfoRetrieve() }.map { it -> User(
            it.firstName,
            it.lastName,
            it.username,
            it.email,
        ) }
    }

    override suspend fun getUser(): Result<User> = withContext(dispatcher) {
        if (networkConnectivityManager.isConnected.value) {
            getProfile().onSuccess{
                return@withContext Result.success(it)
            }
        }

        sessionManager.getUserProfile()?.let { Result.success(it) } ?:
            Result.failure(Exception("user not found"))
    }

    private fun handleLogin400Error(resp: Response<TokenObtainPair>): Resource<Nothing, LoginError> {
        val res = resp.toResource<_, AuthRegisterCreateErrorResponse400>()
        return when (res) {
            is Error -> {
                when (val errRespInstance = res.error.actualInstance) {
                    is AuthTokenCreateValidationError -> {
                        Error(
                            LoginError.ValidationError(
                                errors = errRespInstance.errors.map { err ->
                                    when (err.attr) {
                                        AuthTokenCreateValidationItemErrorAttrEnum.PASSWORD ->
                                            LoginValidationError.Password(err.detail)

                                        AuthTokenCreateValidationItemErrorAttrEnum.NON_FIELD_ERRORS ->
                                            LoginValidationError.NonField(err.detail)

                                        AuthTokenCreateValidationItemErrorAttrEnum.USERNAME ->
                                            LoginValidationError.Username(err.detail)
                                    }
                                }
                            )
                        )
                    }

                    is ParseErrorResponse -> {
                        Error(
                            LoginError.ClientError(
                                errRespInstance.errors.joinToString("\n") { err ->
                                    "${err.attr}: ${err.detail}"
                                }
                            )
                        )
                    }

                    else -> Failure("unexpected error")
                }
            }

            is Failure -> Failure(message = res.message)
            is Success<*> -> Failure("unexpected success")
        }
    }

    override suspend fun finishSync() {
        sessionManager.finishedSync()
    }

    override suspend fun logout() = withContext(dispatcher) {
        notesQueries.clearNotes()
        sessionManager.clearSession()
    }
}