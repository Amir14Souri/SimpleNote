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
 * @param id 
 * @param title 
 * @param description 
 * @param createdAt 
 * @param updatedAt 
 * @param creatorName 
 * @param creatorUsername Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.
 */
@Parcelize


data class Note (

    @SerializedName("id")
    val id: kotlin.Int,

    @SerializedName("title")
    val title: kotlin.String,

    @SerializedName("description")
    val description: kotlin.String,

    @SerializedName("created_at")
    val createdAt: java.time.OffsetDateTime,

    @SerializedName("updated_at")
    val updatedAt: java.time.OffsetDateTime,

    @SerializedName("creator_name")
    val creatorName: kotlin.String,

    /* Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only. */
    @SerializedName("creator_username")
    val creatorUsername: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!Note::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'Note' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(Note::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<Note>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: Note) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): Note  {
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
    * @throws IOException if the JSON Element is invalid with respect to Note
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) id,title,description,created_at,updated_at,creator_name,creator_username in Note is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

      requireNotNull(jsonObj["id"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "id", jsonElement.toString())
      }
      requireNotNull(jsonObj["title"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "title", jsonElement.toString())
      }
      requireNotNull(jsonObj["description"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "description", jsonElement.toString())
      }
      requireNotNull(jsonObj["created_at"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "created_at", jsonElement.toString())
      }
      requireNotNull(jsonObj["updated_at"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "updated_at", jsonElement.toString())
      }
      requireNotNull(jsonObj["creator_name"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "creator_name", jsonElement.toString())
      }
      requireNotNull(jsonObj["creator_username"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "creator_username", jsonElement.toString())
      }

      require(jsonObj["title"].isJsonPrimitive) {
        String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj["title"].toString())
      }
      require(jsonObj["description"].isJsonPrimitive) {
        String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj["description"].toString())
      }
      require(jsonObj["creator_name"].isJsonPrimitive) {
        String.format("Expected the field `creator_name` to be a primitive type in the JSON string but got `%s`", jsonObj["creator_name"].toString())
      }
      require(jsonObj["creator_username"].isJsonPrimitive) {
        String.format("Expected the field `creator_username` to be a primitive type in the JSON string but got `%s`", jsonObj["creator_username"].toString())
      }
    }
    }

}

