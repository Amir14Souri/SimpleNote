@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationError
import ir.sharif.simplenote.data.remote.model.ClientErrorEnum
import ir.sharif.simplenote.data.remote.model.ParseError
import ir.sharif.simplenote.data.remote.model.ParseErrorResponse

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.TypeParceler
import java.io.IOException

/**
 * 
 *
 */
@Parcelize
@TypeParceler<Any?, AuthChangePasswordCreateErrorResponse400.AuthChangePasswordCreateErrorResponse400Parceler>


data class AuthChangePasswordCreateErrorResponse400(var actualInstance: Any? = null): Parcelable {

        object AuthChangePasswordCreateErrorResponse400Parceler : Parceler<AuthChangePasswordCreateErrorResponse400> {
            private const val TYPE_NULL =  "NULL"
            
            private const val TYPE_ONE_OF0 = "ONE_OF0"
            
            private const val TYPE_ONE_OF1 = "ONE_OF1"
            
            override fun create(parcel: android.os.Parcel): AuthChangePasswordCreateErrorResponse400 {
                val type = parcel.readString()
                val instance = when (type) {
                    
                    TYPE_ONE_OF0 -> parcel.readParcelable<AuthChangePasswordCreateValidationError>( AuthChangePasswordCreateValidationError::class.java.classLoader, AuthChangePasswordCreateValidationError::class.java)
                    
                    TYPE_ONE_OF1 -> parcel.readParcelable<ParseErrorResponse>( ParseErrorResponse::class.java.classLoader, ParseErrorResponse::class.java)
                    
                    TYPE_NULL -> null
                    else -> throw IllegalArgumentException("Unknown type identifier for AuthChangePasswordCreateErrorResponse400: $type")
                }
                return AuthChangePasswordCreateErrorResponse400(actualInstance = instance)
            }

            override fun AuthChangePasswordCreateErrorResponse400.write(parcel: android.os.Parcel, flags: Int) {
                when (val instance = this.actualInstance) {
                    
                    is AuthChangePasswordCreateValidationError -> {
                        parcel.writeString(TYPE_ONE_OF0)
                        parcel.writeParcelable(instance as Parcelable?, flags)
                    }
                    
                    is ParseErrorResponse -> {
                        parcel.writeString(TYPE_ONE_OF1)
                        parcel.writeParcelable(instance as Parcelable?, flags)
                    }
                    
                    null -> {
                        parcel.writeString(TYPE_NULL)
                    }
                    else -> throw IllegalArgumentException("Unsupported type for parceling: ${'$'}{instance?.javaClass?.name}")
                }
            }
        }
    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!AuthChangePasswordCreateErrorResponse400::class.java.isAssignableFrom(type.rawType)) {
                return null // this class only serializes 'AuthChangePasswordCreateErrorResponse400' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val adapterAuthChangePasswordCreateValidationError = gson.getDelegateAdapter(this, TypeToken.get(AuthChangePasswordCreateValidationError::class.java))
            val adapterParseErrorResponse = gson.getDelegateAdapter(this, TypeToken.get(ParseErrorResponse::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<AuthChangePasswordCreateErrorResponse400?>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter,value: AuthChangePasswordCreateErrorResponse400?) {
                    if (value?.actualInstance == null) {
                        elementAdapter.write(out, null)
                        return
                    }

                    // check if the actual instance is of the type `AuthChangePasswordCreateValidationError`
                    if (value.actualInstance is AuthChangePasswordCreateValidationError) {
                        val element = adapterAuthChangePasswordCreateValidationError.toJsonTree(value.actualInstance as AuthChangePasswordCreateValidationError?)
                        elementAdapter.write(out, element)
                        return
                    }
                    // check if the actual instance is of the type `ParseErrorResponse`
                    if (value.actualInstance is ParseErrorResponse) {
                        val element = adapterParseErrorResponse.toJsonTree(value.actualInstance as ParseErrorResponse?)
                        elementAdapter.write(out, element)
                        return
                    }
                    throw IOException("Failed to serialize as the type doesn't match oneOf schemas: AuthChangePasswordCreateValidationError, ParseErrorResponse")
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): AuthChangePasswordCreateErrorResponse400 {
                    val jsonElement = elementAdapter.read(jsonReader)
                    var match = 0
                    val errorMessages = ArrayList<String>()
                    var actualAdapter: TypeAdapter<*> = elementAdapter

                    // deserialize AuthChangePasswordCreateValidationError
                    try {
                        // validate the JSON object to see if any exception is thrown
                        AuthChangePasswordCreateValidationError.validateJsonElement(jsonElement)
                        actualAdapter = adapterAuthChangePasswordCreateValidationError
                        match++
                        //log.log(Level.FINER, "Input data matches schema 'AuthChangePasswordCreateValidationError'")
                    } catch (e: Exception) {
                        // deserialization failed, continue
                        errorMessages.add(String.format("Deserialization for AuthChangePasswordCreateValidationError failed with `%s`.", e.message))
                        //log.log(Level.FINER, "Input data does not match schema 'AuthChangePasswordCreateValidationError'", e)
                    }
                    // deserialize ParseErrorResponse
                    try {
                        // validate the JSON object to see if any exception is thrown
                        ParseErrorResponse.validateJsonElement(jsonElement)
                        actualAdapter = adapterParseErrorResponse
                        match++
                        //log.log(Level.FINER, "Input data matches schema 'ParseErrorResponse'")
                    } catch (e: Exception) {
                        // deserialization failed, continue
                        errorMessages.add(String.format("Deserialization for ParseErrorResponse failed with `%s`.", e.message))
                        //log.log(Level.FINER, "Input data does not match schema 'ParseErrorResponse'", e)
                    }

                    if (match == 1) {
                        val ret = AuthChangePasswordCreateErrorResponse400()
                        ret.actualInstance = actualAdapter.fromJsonTree(jsonElement)
                        return ret
                    }

                    throw IOException(String.format("Failed deserialization for AuthChangePasswordCreateErrorResponse400: %d classes match result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", match, errorMessages, jsonElement.toString()))
                }
            }.nullSafe() as TypeAdapter<T>
        }
    }

    companion object {
        /**
        * Validates the JSON Element and throws an exception if issues found
        *
        * @param jsonElement JSON Element
        * @throws IOException if the JSON Element is invalid with respect to AuthChangePasswordCreateErrorResponse400
        */
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            requireNotNull(jsonElement) {
                "Provided json element must not be null"
            }
            var match = 0
            val errorMessages = ArrayList<String>()
            // validate the json string with AuthChangePasswordCreateValidationError
            try {
                // validate the JSON object to see if any exception is thrown
                AuthChangePasswordCreateValidationError.validateJsonElement(jsonElement)
                match++
            } catch (e: Exception) {
                // Validation failed, continue
                errorMessages.add(String.format("Validation for AuthChangePasswordCreateValidationError failed with `%s`.", e.message))
            }
            // validate the json string with ParseErrorResponse
            try {
                // validate the JSON object to see if any exception is thrown
                ParseErrorResponse.validateJsonElement(jsonElement)
                match++
            } catch (e: Exception) {
                // Validation failed, continue
                errorMessages.add(String.format("Validation for ParseErrorResponse failed with `%s`.", e.message))
            }

            if (match != 1) {
                throw IOException(String.format("Failed validation for AuthChangePasswordCreateErrorResponse400: %d classes match result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", match, errorMessages, jsonElement.toString()))
            }
        }
    }
}
