package ir.sharif.simplenote.data.remote.model

import com.google.gson.JsonElement
import java.io.IOException

data class TokenObtainPairRequest(
  val username: String,
  val password: String
)

@Throws(IOException::class)
fun validateJsonElement(jsonElement: JsonElement?) {
  if (jsonElement == null) {
    throw IOException("The required fields 'username', 'password' in TokenObtainPairRequest are not found in the empty JSON string")
  }
  val jsonObj = jsonElement.asJsonObject
  requireNotNull(jsonObj["username"]) { "The required field `username` is not found in the JSON string: $jsonObj" }
  requireNotNull(jsonObj["password"]) { "The required field `password` is not found in the JSON string: $jsonObj" }
  require(jsonObj["username"].isJsonPrimitive) { "Expected the field `username` to be a primitive type in the JSON string but got ${jsonObj["username"]}" }
  require(jsonObj["password"].isJsonPrimitive) { "Expected the field `password` to be a primitive type in the JSON string but got ${jsonObj["password"]}" }
}
