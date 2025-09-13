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
 * @param access 
 * @param refresh 
 */
@Parcelize


data class TokenObtainPair (

    @SerializedName("access")
    val access: kotlin.String,

    @SerializedName("refresh")
    val refresh: kotlin.String

) : Parcelable {


    class CustomTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (!TokenObtainPair::class.java.isAssignableFrom(type.rawType)) {
              return null // this class only serializes 'TokenObtainPair' and its subtypes
            }
            val elementAdapter = gson.getAdapter(JsonElement::class.java)
            val thisAdapter = gson.getDelegateAdapter(this, TypeToken.get(TokenObtainPair::class.java))

            @Suppress("UNCHECKED_CAST")
            return object : TypeAdapter<TokenObtainPair>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: TokenObtainPair) {
                    val obj = thisAdapter.toJsonTree(value).getAsJsonObject()
                    elementAdapter.write(out, obj)
                }

                @Throws(IOException::class)
                override fun read(jsonReader: JsonReader): TokenObtainPair  {
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
            kotlinFields.add("access")
            kotlinFields.add("refresh")

            // a set of required properties/fields (JSON key names)
            kotlinRequiredFields.add("access")
            kotlinRequiredFields.add("refresh")
        }

       /**
        * Validates the JSON Element and throws an exception if issues found
        *
        * @param jsonElement JSON Element
        * @throws IOException if the JSON Element is invalid with respect to TokenObtainPair
        */
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
              require(kotlinRequiredFields.isEmpty()) { // has required fields but JSON element is null
                String.format("The required field(s) %s in TokenObtainPair is not found in the empty JSON string", TokenObtainPair.kotlinRequiredFields.toString())
              }
            }

            // check to make sure all required properties/fields are present in the JSON string
            for (requiredField in kotlinRequiredFields) {
              requireNotNull(jsonElement!!.getAsJsonObject()[requiredField]) {
                String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString())
              }
            }
            val jsonObj = jsonElement!!.getAsJsonObject()
            require(jsonObj["access"].isJsonPrimitive) {
              String.format("Expected the field `access` to be a primitive type in the JSON string but got `%s`", jsonObj["access"].toString())
            }
            require(jsonObj["refresh"].isJsonPrimitive) {
              String.format("Expected the field `refresh` to be a primitive type in the JSON string but got `%s`", jsonObj["refresh"].toString())
            }
        }
    }

}

