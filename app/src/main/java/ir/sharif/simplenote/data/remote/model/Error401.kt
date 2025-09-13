package ir.sharif.simplenote.data.remote.model


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
 * @param code 
 * @param detail 
 * @param attr 
 */
@Parcelize


data class Error401 (

    @SerializedName("code")
    val code: kotlin.String,

    @SerializedName("detail")
    val detail: kotlin.String,

    @SerializedName("attr")
    val attr: kotlin.String?

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!Error401::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'Error401' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(Error401::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<Error401>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: Error401) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): Error401  {
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
              throw IOException("The JSON string is null for Error401 but required fields are present")
            }

            val jsonObj = jsonElement.getAsJsonObject()

            requireNotNull(jsonObj["code"]) { "The required field `code` is not found in the JSON string: $jsonElement" }
            requireNotNull(jsonObj["detail"]) { "The required field `detail` is not found in the JSON string: $jsonElement" }

            require(jsonObj["code"].isJsonPrimitive) {
              String.format("Expected the field `code` to be a primitive type in the JSON string but got `%s`", jsonObj["code"].toString())
            }
            require(jsonObj["detail"].isJsonPrimitive) {
              String.format("Expected the field `detail` to be a primitive type in the JSON string but got `%s`", jsonObj["detail"].toString())
            }
            if (jsonObj["attr"] != null && !jsonObj["attr"].isJsonNull) {
              require(jsonObj.get("attr").isJsonPrimitive) {
                String.format("Expected the field `attr` to be a primitive type in the JSON string but got `%s`", jsonObj["attr"].toString())
              }
            }
        }
    }

}

