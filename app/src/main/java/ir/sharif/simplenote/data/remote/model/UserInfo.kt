@file:Suppress(
  "ArrayInDataClass",
  "EnumEntryName",
  "RemoveRedundantQualifierName",
  "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model

import android.os.Parcelable
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.IOException

@Parcelize
data class UserInfo(
  @SerializedName("id")
  val id: Int,
  @SerializedName("username")
  val username: String,
  @SerializedName("email")
  val email: String,
  @SerializedName("first_name")
  val firstName: String? = null,
  @SerializedName("last_name")
  val lastName: String? = null
) : Parcelable

@Throws(IOException::class)
fun validateUserInfoJsonElement(jsonElement: JsonElement?) {
  if (jsonElement == null) {
    throw IOException("The required fields id, username, email in UserInfo are not found in the empty JSON string")
  }
  val jsonObj = jsonElement.asJsonObject
  requireNotNull(jsonObj["id"]) { "The required field `id` is not found in the JSON string: $jsonObj" }
  requireNotNull(jsonObj["username"]) { "The required field `username` is not found in the JSON string: $jsonObj" }
  requireNotNull(jsonObj["email"]) { "The required field `email` is not found in the JSON string: $jsonObj" }
  require(jsonObj["username"].isJsonPrimitive) { "Expected the field `username` to be a primitive type in the JSON string but got ${jsonObj["username"]}" }
  require(jsonObj["email"].isJsonPrimitive) { "Expected the field `email` to be a primitive type in the JSON string but got ${jsonObj["email"]}" }
  if (jsonObj["first_name"] != null && !jsonObj["first_name"].isJsonNull) {
    require(jsonObj.get("first_name").isJsonPrimitive) { "Expected the field `first_name` to be a primitive type in the JSON string but got ${jsonObj["first_name"]}" }
  }
  if (jsonObj["last_name"] != null && !jsonObj["last_name"].isJsonNull) {
    require(jsonObj.get("last_name").isJsonPrimitive) { "Expected the field `last_name` to be a primitive type in the JSON string but got ${jsonObj["last_name"]}" }
  }
}
