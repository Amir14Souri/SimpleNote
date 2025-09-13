@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.ClientErrorEnum
import ir.sharif.simplenote.data.remote.model.Error405

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


data class ErrorResponse405 (

    @SerializedName("type")
    val type: ClientErrorEnum,

    @SerializedName("errors")
    val errors: kotlin.collections.List<Error405>

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!ErrorResponse405::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'ErrorResponse405' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(ErrorResponse405::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<ErrorResponse405>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: ErrorResponse405) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): ErrorResponse405  {
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
    * @throws IOException if the JSON Element is invalid with respect to ErrorResponse405
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) type, errors in ErrorResponse405 is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

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
        Error405.validateJsonElement(jsonObj.getAsJsonArray("errors").get(i))
      }
    }
    }

}

