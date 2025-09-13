@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `not_acceptable` - Not Acceptable
 *
 * Values: NOT_ACCEPTABLE
 */

enum class ErrorCode406Enum(val value: kotlin.String) {

    @SerializedName(value = "not_acceptable")
    NOT_ACCEPTABLE("not_acceptable");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is ErrorCode406Enum) "$data" else null

        /**
         * Returns a valid [ErrorCode406Enum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): ErrorCode406Enum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

