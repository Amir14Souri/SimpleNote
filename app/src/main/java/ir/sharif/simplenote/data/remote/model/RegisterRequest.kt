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
 * @param username Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.
 * @param password 
 * @param email 
 * @param firstName 
 * @param lastName 
 */
@Parcelize


data class RegisterRequest (

    /* Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only. */
    @SerializedName("username")
    val username: kotlin.String,

    @SerializedName("password")
    val password: kotlin.String,

    @SerializedName("email")
    val email: kotlin.String,

    @SerializedName("first_name")
    val firstName: kotlin.String? = null,

    @SerializedName("last_name")
    val lastName: kotlin.String? = null

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!RegisterRequest::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'RegisterRequest' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(RegisterRequest::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<RegisterRequest>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: RegisterRequest) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): RegisterRequest  {
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
    * @throws IOException if the JSON Element is invalid with respect to RegisterRequest
    */
    @Throws(IOException::class)
    fun validateJsonElement(jsonElement: JsonElement?) {
      if (jsonElement == null) {
        throw IOException("The required field(s) username,password,email in RegisterRequest is not found in the empty JSON string")
      }

      val jsonObj = jsonElement.getAsJsonObject()

      requireNotNull(jsonObj["username"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "username", jsonElement.toString())
      }
      requireNotNull(jsonObj["password"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "password", jsonElement.toString())
      }
      requireNotNull(jsonObj["email"]) {
        String.format("The required field `%s` is not found in the JSON string: %s", "email", jsonElement.toString())
      }

      require(jsonObj["username"].isJsonPrimitive) {
        String.format("Expected the field `username` to be a primitive type in the JSON string but got `%s`", jsonObj["username"].toString())
      }
      require(jsonObj["password"].isJsonPrimitive) {
        String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj["password"].toString())
      }
      require(jsonObj["email"].isJsonPrimitive) {
        String.format("Expected the field `email` to be a primitive type in the JSON string but got `%s`", jsonObj["email"].toString())
      }
      if (jsonObj["first_name"] != null && !jsonObj["first_name"].isJsonNull) {
        require(jsonObj.get("first_name").isJsonPrimitive) {
          String.format("Expected the field `first_name` to be a primitive type in the JSON string but got `%s`", jsonObj["first_name"].toString())
        }
      }
      if (jsonObj["last_name"] != null && !jsonObj["last_name"].isJsonNull) {
        require(jsonObj.get("last_name").isJsonPrimitive) {
          String.format("Expected the field `last_name` to be a primitive type in the JSON string but got `%s`", jsonObj["last_name"].toString())
        }
      }
    }
    }

}

