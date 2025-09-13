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
data class ErrorResponse500(
    @SerializedName("type")
    val type: String,
    @SerializedName("errors")
    val errors: List<Error500>
) : Parcelable {
    companion object {
        @Throws(IOException::class)
        fun validateJsonElement(jsonElement: JsonElement?) {
            if (jsonElement == null) {
                throw IOException("The required field(s) type,errors in ErrorResponse500 is not found in the empty JSON string")
            }

            val jsonObj = jsonElement.asJsonObject
            requireNotNull(jsonObj["type"]) {
                "The required field `type` is not found in the JSON string: $jsonObj"
            }

            require(ServerErrorEnum.values().any { it.value == jsonObj["type"].asString }) {
                "Expected the field `type` to be valid `ServerErrorEnum` enum value in the JSON string but got ${jsonObj["type"]}"
            }

            if (!jsonObj["errors"].isJsonArray) {
                throw IllegalArgumentException("Expected the field `errors` to be an array in the JSON string but got ${jsonObj["errors"]}")
            }

            for (i in 0 until jsonObj.getAsJsonArray("errors").size()) {
                Error500.validateJsonElement(jsonObj.getAsJsonArray("errors")[i])
            }
        }
    }
}
