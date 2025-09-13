package ir.sharif.simplenote.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import retrofit2.Response

inline fun <T, reified E> Response<T>.toResource(): Resource<T, E> {
    if (isSuccessful) {
        val body = body() ?: return Resource.Failure(message="Response body is null")
        return Resource.Success(body)
    }
    try {
        val bodyStr = errorBody()?.string().orEmpty()
        if (bodyStr.isEmpty()) return Resource.Failure(message = "", throwable = Exception("error body is null"))
        val gson = GsonBuilder().create()
        val jsonElem = JsonParser.parseString(bodyStr)
        val errBody: E = gson.fromJson(jsonElem, E::class.java)
        return Resource.Error<E>(errBody)
    } catch (e: Exception) {
        return Resource.Failure(message = errorBody()?.string().orEmpty(), throwable = e)
    }
}