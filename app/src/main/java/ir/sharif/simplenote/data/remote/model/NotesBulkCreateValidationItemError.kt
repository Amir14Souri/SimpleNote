@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationItemErrorAttrEnum

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
 * @param attr 
 * @param code 
 * @param detail 
 */
@Parcelize


data class NotesBulkCreateValidationItemError (

    @SerializedName("attr")
    val attr: NotesBulkCreateValidationItemErrorAttrEnum,

    @SerializedName("code")
    val code: kotlin.String,

    @SerializedName("detail")
    val detail: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!NotesBulkCreateValidationItemError::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'NotesBulkCreateValidationItemError' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(NotesBulkCreateValidationItemError::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<NotesBulkCreateValidationItemError>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: NotesBulkCreateValidationItemError) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): NotesBulkCreateValidationItemError  {
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
    * @throws IOException if the JSON Element is invalid with respect to NotesBulkCreateValidationItemError
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) attr, code, detail in NotesBulkCreateValidationItemError is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

      // validate the required field `attr`
      require(NotesBulkCreateValidationItemErrorAttrEnum.values().any { it.value == jsonObj["attr"].asString }) {
        String.format("Expected the field `attr` to be valid `NotesBulkCreateValidationItemErrorAttrEnum` enum value in the JSON string but got `%s`", jsonObj["attr"].toString())
      }
      require(jsonObj["code"].isJsonPrimitive) {
        String.format("Expected the field `code` to be a primitive type in the JSON string but got `%s`", jsonObj["code"].toString())
      }
      require(jsonObj["detail"].isJsonPrimitive) {
        String.format("Expected the field `detail` to be a primitive type in the JSON string but got `%s`", jsonObj["detail"].toString())
      }
    }
    }

}

