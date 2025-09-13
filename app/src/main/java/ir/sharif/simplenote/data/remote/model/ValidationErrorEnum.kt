@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `validation_error` - Validation Error
 *
 * Values: VALIDATION_ERROR
 */

enum class ValidationErrorEnum(val value: kotlin.String) {

    @SerializedName(value = "validation_error")
    VALIDATION_ERROR("validation_error");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is ValidationErrorEnum) "$data" else null

        /**
         * Returns a valid [ValidationErrorEnum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): ValidationErrorEnum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

