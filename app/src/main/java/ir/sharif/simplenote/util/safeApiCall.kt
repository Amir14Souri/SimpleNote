package ir.sharif.simplenote.util

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCallResponse(apiCall: suspend () -> Response<T>): Result<Response<T>> {
    return try {
        val response = apiCall()
        Result.success(response)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Response body is null"))
            }
        } else {
            Result.failure(HttpException(response))
        }
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
