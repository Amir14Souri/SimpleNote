@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationItemError
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


data class NotesPartialUpdateValidationError (

    @SerializedName("type")
    val type: ValidationErrorEnum,

    @SerializedName("errors")
    val errors: kotlin.collections.List<NotesPartialUpdateValidationItemError>

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!NotesPartialUpdateValidationError::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'NotesPartialUpdateValidationError' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(NotesPartialUpdateValidationError::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<NotesPartialUpdateValidationError>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: NotesPartialUpdateValidationError) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): NotesPartialUpdateValidationError  {
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
            kotlinFields.add("type")
            kotlinFields.add("errors")

            // a set of required properties/fields (JSON key names)
            kotlinRequiredFields.add("type")
            kotlinRequiredFields.add("errors")
        }

       /**
        * Validates the JSON Element and throws an exception if issues found
        *
        * @param jsonElement JSON Element
        * @throws IOException if the JSON Element is invalid with respect to NotesPartialUpdateValidationError
        */
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
              require(kotlinRequiredFields.isEmpty()) { // has required fields but JSON element is null
                String.format("The required field(s) %s in NotesPartialUpdateValidationError is not found in the empty JSON string", NotesPartialUpdateValidationError.kotlinRequiredFields.toString())
              }
            }

            // check to make sure all required properties/fields are present in the JSON string
            for (requiredField in kotlinRequiredFields) {
              requireNotNull(jsonElement!!.getAsJsonObject()[requiredField]) {
                String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString())
              }
            }
            val jsonObj = jsonElement!!.getAsJsonObject()
            // validate the required field `type`
            require(ValidationErrorEnum.values().any { it.value == jsonObj["type"].asString }) {
                String.format("Expected the field `type` to be valid `ValidationErrorEnum` enum value in the JSON string but got `%s`", jsonObj["type"].toString())
            }
            // ensure the json data is an array
            if (!jsonObj.get("errors").isJsonArray) {
              throw IllegalArgumentException(String.format("Expected the field `errors` to be an array in the JSON string but got `%s`", jsonObj["errors"].toString()))
            }

            // validate the required field `errors` (array)
            for (i in 0 until jsonObj.getAsJsonArray("errors").size()) {
              NotesPartialUpdateValidationItemError.validateJsonElement(jsonObj.getAsJsonArray("errors").get(i))
            }
        }
    }

}

