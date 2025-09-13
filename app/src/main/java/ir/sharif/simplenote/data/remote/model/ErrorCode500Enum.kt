@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `error` - Error
 *
 * Values: ERROR
 */

enum class ErrorCode500Enum(val value: kotlin.String) {

    @SerializedName(value = "error")
    ERROR("error");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is ErrorCode500Enum) "$data" else null

        /**
         * Returns a valid [ErrorCode500Enum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): ErrorCode500Enum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

