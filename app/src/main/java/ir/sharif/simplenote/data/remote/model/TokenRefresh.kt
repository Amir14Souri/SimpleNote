@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.annotations.SerializedName
import java.io.IOException
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenRefresh(
    @SerializedName("access")
    val access: String
) : Parcelable {

    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!TokenRefresh::class.java.isAssignableFrom(type.rawType)) {
                return null
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(TokenRefresh::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<TokenRefresh>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: TokenRefresh) {
                    val obj = thisAdapter.toJsonTree(value).asJsonObject
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): TokenRefresh {
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
                throw IOException("The required field 'access' in TokenRefresh is not found in the empty JSON string")
            }
            val jsonObj = jsonElement.asJsonObject
            requireNotNull(jsonObj["access"]) { "The required field `access` is not found in the JSON string: $jsonObj" }
            require(jsonObj["access"].isJsonPrimitive) {
                "Expected the field `access` to be a primitive type in the JSON string but got ${jsonObj["access"]}"
            }
        }
    }
}
