package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.ClientErrorEnum
import ir.sharif.simplenote.data.remote.model.Error401

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.annotations.JsonAdapter
import java.io.IOException
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param type 
 * @param errors 
 */
@Parcelize


data class ErrorResponse401 (

    @SerializedName("type")
    val type: ClientErrorEnum,

    @SerializedName("errors")
    val errors: kotlin.collections.List<Error401>

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!ErrorResponse401::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'ErrorResponse401' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(ErrorResponse401::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<ErrorResponse401>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: ErrorResponse401) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): ErrorResponse401  {
                    val jsonElement = elementAdapter.read(jsonReader)
                    validateJsonElement(jsonElement)
                    return thisAdapter.fromJsonTree(jsonElement)
                }
            }.nullSafe() as TypeAdapter<T>
        }
    }

    companion object {
       @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
              throw IOException("The JSON string is null for ErrorResponse401 but required fields are present")
            }

            val jsonObj = jsonElement.getAsJsonObject()

            requireNotNull(jsonObj["type"]) { "The required field `type` is not found in the JSON string: $jsonElement" }
            requireNotNull(jsonObj["errors"]) { "The required field `errors` is not found in the JSON string: $jsonElement" }

            // validate the required field `type`
            require(ClientErrorEnum.values().any { it.value == jsonObj["type"].asString }) {
                String.format("Expected the field `type` to be valid `ClientErrorEnum` enum value in the JSON string but got `%s`", jsonObj["type"].toString())
            }

            // ensure the json data is an array
            if (!jsonObj.get("errors").isJsonArray) {
              throw IllegalArgumentException(String.format("Expected the field `errors` to be an array in the JSON string but got `%s`", jsonObj["errors"].toString()))
            }

            // validate the required field `errors` (array)
            for (i in 0 until jsonObj.getAsJsonArray("errors").size()) {
              Error401.validateJsonElement(jsonObj.getAsJsonArray("errors").get(i))
            }
        }
    }

}

