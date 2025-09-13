@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `method_not_allowed` - Method Not Allowed
 *
 * Values: METHOD_NOT_ALLOWED
 */

enum class ErrorCode405Enum(val value: kotlin.String) {

    @SerializedName(value = "method_not_allowed")
    METHOD_NOT_ALLOWED("method_not_allowed");

    /**
     * Override [toString()] to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): kotlin.String = value

    companion object {
        /**
         * Converts the provided [data] to a [String] on success, null otherwise.
         */
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is ErrorCode405Enum) "$data" else null

        /**
         * Returns a valid [ErrorCode405Enum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): ErrorCode405Enum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

