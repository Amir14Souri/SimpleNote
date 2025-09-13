@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationItemError
import ir.sharif.simplenote.data.remote.model.ValidationErrorEnum

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


data class NotesBulkCreateValidationError (

    @SerializedName("type")
    val type: ValidationErrorEnum,

    @SerializedName("errors")
    val errors: kotlin.collections.List<NotesBulkCreateValidationItemError>

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!NotesBulkCreateValidationError::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'NotesBulkCreateValidationError' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(NotesBulkCreateValidationError::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<NotesBulkCreateValidationError>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: NotesBulkCreateValidationError) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): NotesBulkCreateValidationError  {
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
    * @throws IOException if the JSON Element is invalid with respect to NotesBulkCreateValidationError
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) type,errors in NotesBulkCreateValidationError is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()
      requireNotNull(jsonObj["type"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "type", jsonElement.toString())
      }

      require(ValidationErrorEnum.values().any { it.value == jsonObj["type"].asString }) {
        String.format("Expected the field `type` to be valid `ValidationErrorEnum` enum value in the JSON string but got `%s`", jsonObj["type"].toString())
      }

      // ensure the json data is an array
      if (!jsonObj.get("errors").isJsonArray) {
        throw IllegalArgumentException(String.format("Expected the field `errors` to be an array in the JSON string but got `%s`", jsonObj["errors"].toString()))
      }

      for (i in 0 until jsonObj.getAsJsonArray("errors").size()) {
        NotesBulkCreateValidationItemError.validateJsonElement(jsonObj.getAsJsonArray("errors").get(i))
      }
    }
    }

}

