@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `non_field_errors` - non_field_errors * `username` - username * `password` - password * `email` - email * `first_name` - first_name * `last_name` - last_name
 *
 * Values: NON_FIELD_ERRORS,USERNAME,PASSWORD,EMAIL,FIRST_NAME,LAST_NAME
 */

enum class AuthRegisterCreateValidationItemErrorAttrEnum(val value: kotlin.String) {

    @SerializedName(value = "non_field_errors")
    NON_FIELD_ERRORS("non_field_errors"),

    @SerializedName(value = "username")
    USERNAME("username"),

    @SerializedName(value = "password")
    PASSWORD("password"),

    @SerializedName(value = "email")
    EMAIL("email"),

    @SerializedName(value = "first_name")
    FIRST_NAME("first_name"),

    @SerializedName(value = "last_name")
    LAST_NAME("last_name");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is AuthRegisterCreateValidationItemErrorAttrEnum) "$data" else null

        /**
         * Returns a valid [AuthRegisterCreateValidationItemErrorAttrEnum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): AuthRegisterCreateValidationItemErrorAttrEnum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

