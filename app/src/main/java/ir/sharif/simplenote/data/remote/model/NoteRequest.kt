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
 * @param title 
 * @param description 
 */
@Parcelize


data class NoteRequest (

    @SerializedName("title")
    val title: kotlin.String,

    @SerializedName("description")
    val description: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!NoteRequest::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'NoteRequest' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(NoteRequest::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<NoteRequest>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: NoteRequest) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): NoteRequest  {
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
    * @throws IOException if the JSON Element is invalid with respect to NoteRequest
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) title,description in NoteRequest is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

      requireNotNull(jsonObj["title"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "title", jsonElement.toString())
      }
      requireNotNull(jsonObj["description"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "description", jsonElement.toString())
      }

      require(jsonObj["title"].isJsonPrimitive) {
        String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj["title"].toString())
      }
      require(jsonObj["description"].isJsonPrimitive) {
        String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj["description"].toString())
      }
    }
    }

}

