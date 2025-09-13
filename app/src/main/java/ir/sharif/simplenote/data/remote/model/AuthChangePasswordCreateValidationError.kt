@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationItemError
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


data class AuthChangePasswordCreateValidationError (

    @SerializedName("type")
    val type: ValidationErrorEnum,

    @SerializedName("errors")
    val errors: kotlin.collections.List<AuthChangePasswordCreateValidationItemError>

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!AuthChangePasswordCreateValidationError::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'AuthChangePasswordCreateValidationError' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(AuthChangePasswordCreateValidationError::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<AuthChangePasswordCreateValidationError>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: AuthChangePasswordCreateValidationError) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): AuthChangePasswordCreateValidationError  {
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
    * @throws IOException if the JSON Element is invalid with respect to AuthChangePasswordCreateValidationError
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) type,errors in AuthChangePasswordCreateValidationError is not found in the empty JSON string")
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
        AuthChangePasswordCreateValidationItemError.validateJsonElement(jsonObj.getAsJsonArray("errors").get(i))
      }
    }
    }

}

