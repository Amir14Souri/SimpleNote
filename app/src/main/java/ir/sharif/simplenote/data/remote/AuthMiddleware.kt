package ir.sharif.simplenote.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import ir.sharif.simplenote.data.local.SessionManager
import ir.sharif.simplenote.domain.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton
import javax.inject.Inject
import dagger.Lazy
import ir.sharif.simplenote.data.remote.model.ErrorResponse401
import kotlinx.coroutines.runBlocking
import okhttp3.Request

@Singleton
class AuthMiddleware @Inject constructor(val sessionManager: SessionManager, val userRepository: Lazy<UserRepository>): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()

    val serializer = GsonBuilder().create()

        val token =  sessionManager.getAccessToken() ?: return chain.proceed(request)

        val response = chain.proceed(addTokenToRequest(request, token))

        if (response.code == 401) {
            val respBody = response.body?.string() ?: return response
            try {
                val err = serializer.fromJson(respBody, ErrorResponse401::class.java)
                if (err.errors.find { it -> it.code == "token_not_valid" } == null) {
                    return response
                }
            } catch (e: JsonSyntaxException) {
                return response
            }
            return runBlocking {
                val refreshRes = userRepository.get().refreshToken()

                refreshRes.onSuccess {
                    refreshedAccessToken ->
                        return@runBlocking chain.proceed(
                            addTokenToRequest(request, refreshedAccessToken))
                }

                return@runBlocking response
            }
        }

        return response
    }

    fun addTokenToRequest(request: Request, token: String): Request {
        return request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
    }
}