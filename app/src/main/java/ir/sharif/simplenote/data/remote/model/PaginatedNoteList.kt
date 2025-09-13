@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.Note

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
 * @param count 
 * @param results 
 * @param next 
 * @param previous 
 */
@Parcelize


data class PaginatedNoteList (

    @SerializedName("count")
    val count: kotlin.Int,

    @SerializedName("results")
    val results: kotlin.collections.List<Note>,

    @SerializedName("next")
    val next: java.net.URI? = null,

    @SerializedName("previous")
    val previous: java.net.URI? = null

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!PaginatedNoteList::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'PaginatedNoteList' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(PaginatedNoteList::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<PaginatedNoteList>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: PaginatedNoteList) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): PaginatedNoteList  {
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
    * @throws IOException if the JSON Element is invalid with respect to PaginatedNoteList
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) count,results in PaginatedNoteList is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

      requireNotNull(jsonObj["count"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "count", jsonElement.toString())
      }
      requireNotNull(jsonObj["results"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "results", jsonElement.toString())
      }

      // ensure the json data is an array
      if (!jsonObj.get("results").isJsonArray) {
        throw IllegalArgumentException(String.format("Expected the field `results` to be an array in the JSON string but got `%s`", jsonObj["results"].toString()))
      }

      // validate the required field `results` (array)
      for (i in 0 until jsonObj.getAsJsonArray("results").size()) {
        Note.validateJsonElement(jsonObj.getAsJsonArray("results").get(i))
      }
      if (jsonObj["next"] != null && !jsonObj["next"].isJsonNull) {
        require(jsonObj.get("next").isJsonPrimitive) {
          String.format("Expected the field `next` to be a primitive type in the JSON string but got `%s`", jsonObj["next"].toString())
        }
      }
      if (jsonObj["previous"] != null && !jsonObj["previous"].isJsonNull) {
        require(jsonObj.get("previous").isJsonPrimitive) {
          String.format("Expected the field `previous` to be a primitive type in the JSON string but got `%s`", jsonObj["previous"].toString())
        }
      }
    }
    }

}

