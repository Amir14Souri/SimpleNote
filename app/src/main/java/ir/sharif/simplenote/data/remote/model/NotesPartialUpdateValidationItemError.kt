@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.Attr750Enum

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


data class NotesPartialUpdateValidationItemError (

    @SerializedName("attr")
    val attr: Attr750Enum,

    @SerializedName("code")
    val code: kotlin.String,

    @SerializedName("detail")
    val detail: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!NotesPartialUpdateValidationItemError::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'NotesPartialUpdateValidationItemError' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(NotesPartialUpdateValidationItemError::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<NotesPartialUpdateValidationItemError>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: NotesPartialUpdateValidationItemError) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): NotesPartialUpdateValidationItemError  {
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
            kotlinFields.add("attr")
            kotlinFields.add("code")
            kotlinFields.add("detail")

            // a set of required properties/fields (JSON key names)
            kotlinRequiredFields.add("attr")
            kotlinRequiredFields.add("code")
            kotlinRequiredFields.add("detail")
        }

       /**
        * Validates the JSON Element and throws an exception if issues found
        *
        * @param jsonElement JSON Element
        * @throws IOException if the JSON Element is invalid with respect to NotesPartialUpdateValidationItemError
        */
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
              require(kotlinRequiredFields.isEmpty()) { // has required fields but JSON element is null
                String.format("The required field(s) %s in NotesPartialUpdateValidationItemError is not found in the empty JSON string", NotesPartialUpdateValidationItemError.kotlinRequiredFields.toString())
              }
            }

            // check to make sure all required properties/fields are present in the JSON string
            for (requiredField in kotlinRequiredFields) {
              requireNotNull(jsonElement!!.getAsJsonObject()[requiredField]) {
                String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString())
              }
            }
            val jsonObj = jsonElement!!.getAsJsonObject()
            // validate the required field `attr`
            require(Attr750Enum.values().any { it.value == jsonObj["attr"].asString }) {
                String.format("Expected the field `attr` to be valid `Attr750Enum` enum value in the JSON string but got `%s`", jsonObj["attr"].toString())
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

