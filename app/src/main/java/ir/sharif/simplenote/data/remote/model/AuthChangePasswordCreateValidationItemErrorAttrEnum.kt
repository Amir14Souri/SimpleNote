@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `non_field_errors` - non_field_errors * `old_password` - old_password * `new_password` - new_password
 *
 * Values: NON_FIELD_ERRORS,OLD_PASSWORD,NEW_PASSWORD
 */

enum class AuthChangePasswordCreateValidationItemErrorAttrEnum(val value: kotlin.String) {

    @SerializedName(value = "non_field_errors")
    NON_FIELD_ERRORS("non_field_errors"),

    @SerializedName(value = "old_password")
    OLD_PASSWORD("old_password"),

    @SerializedName(value = "new_password")
    NEW_PASSWORD("new_password");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is AuthChangePasswordCreateValidationItemErrorAttrEnum) "$data" else null

        /**
         * Returns a valid [AuthChangePasswordCreateValidationItemErrorAttrEnum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): AuthChangePasswordCreateValidationItemErrorAttrEnum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

