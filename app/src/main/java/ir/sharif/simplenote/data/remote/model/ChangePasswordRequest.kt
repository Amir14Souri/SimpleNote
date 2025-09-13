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
import com.google.gson.annotations.JsonAdapter
import java.io.IOException
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param oldPassword 
 * @param newPassword 
 */
@Parcelize


data class ChangePasswordRequest (

    @SerializedName("old_password")
    val oldPassword: kotlin.String,

    @SerializedName("new_password")
    val newPassword: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!ChangePasswordRequest::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'ChangePasswordRequest' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(ChangePasswordRequest::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<ChangePasswordRequest>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: ChangePasswordRequest) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): ChangePasswordRequest  {
                    val jsonElement = elementAdapter.read(jsonReader)
                    validateJsonElement(jsonElement)
                    return thisAdapter.fromJsonTree(jsonElement)
                }
            }.nullSafe() as TypeAdapter<T>
        }
    }

    companion object {
     /**
    * Validates the JSON Element and throws an exception if issues found
    *
    * @param jsonElement JSON Element
    * @throws IOException if the JSON Element is invalid with respect to ChangePasswordRequest
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) old_password,new_password in ChangePasswordRequest is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()
      requireNotNull(jsonObj["old_password"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "old_password", jsonElement.toString())
      }
      requireNotNull(jsonObj["new_password"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "new_password", jsonElement.toString())
      }

      require(jsonObj["old_password"].isJsonPrimitive) {
        String.format("Expected the field `old_password` to be a primitive type in the JSON string but got `%s`", jsonObj["old_password"].toString())
      }
      require(jsonObj["new_password"].isJsonPrimitive) {
        String.format("Expected the field `new_password` to be a primitive type in the JSON string but got `%s`", jsonObj["new_password"].toString())
      }
    }
    }

}

