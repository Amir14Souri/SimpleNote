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


data class PatchedNoteRequest (

    @SerializedName("title")
    val title: kotlin.String? = null,

    @SerializedName("description")
    val description: kotlin.String? = null

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!PatchedNoteRequest::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'PatchedNoteRequest' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(PatchedNoteRequest::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<PatchedNoteRequest>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: PatchedNoteRequest) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): PatchedNoteRequest  {
                    val jsonElement = elementAdapter.read(jsonReader)
                    validateJsonElement(jsonElement)
                    return thisAdapter.fromJsonTree(jsonElement)
                }
            }.nullSafe() as TypeAdapter<T>
        }
    }

    companion object {
  var kotlinFields = HashSet<String>()
  var kotlinRequiredFields = HashSet<String>()

        init {
            // a set of all properties/fields (JSON key names)
            kotlinFields.add("title")
            kotlinFields.add("description")

        }

       /**
        * Validates the JSON Element and throws an exception if issues found
        *
        * @param jsonElement JSON Element
        * @throws IOException if the JSON Element is invalid with respect to PatchedNoteRequest
        */
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
              require(kotlinRequiredFields.isEmpty()) { // has required fields but JSON element is null
                String.format("The required field(s) %s in PatchedNoteRequest is not found in the empty JSON string", PatchedNoteRequest.kotlinRequiredFields.toString())
              }
            }
            val jsonObj = jsonElement!!.getAsJsonObject()
            if (jsonObj["title"] != null && !jsonObj["title"].isJsonNull) {
              require(jsonObj.get("title").isJsonPrimitive) {
                String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj["title"].toString())
              }
            }
            if (jsonObj["description"] != null && !jsonObj["description"].isJsonNull) {
              require(jsonObj.get("description").isJsonPrimitive) {
                String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj["description"].toString())
              }
            }
        }
    }

}

