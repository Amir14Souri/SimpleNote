@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `non_field_errors` - non_field_errors * `INDEX.non_field_errors` - INDEX.non_field_errors * `INDEX.title` - INDEX.title * `INDEX.description` - INDEX.description
 *
 * Values: NON_FIELD_ERRORS,INDEX_PERIOD_NON_FIELD_ERRORS,INDEX_PERIOD_TITLE,INDEX_PERIOD_DESCRIPTION
 */

enum class NotesBulkCreateValidationItemErrorAttrEnum(val value: kotlin.String) {

    @SerializedName(value = "non_field_errors")
    NON_FIELD_ERRORS("non_field_errors"),

    @SerializedName(value = "INDEX.non_field_errors")
    INDEX_PERIOD_NON_FIELD_ERRORS("INDEX.non_field_errors"),

    @SerializedName(value = "INDEX.title")
    INDEX_PERIOD_TITLE("INDEX.title"),

    @SerializedName(value = "INDEX.description")
    INDEX_PERIOD_DESCRIPTION("INDEX.description");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is NotesBulkCreateValidationItemErrorAttrEnum) "$data" else null

        /**
         * Returns a valid [NotesBulkCreateValidationItemErrorAttrEnum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): NotesBulkCreateValidationItemErrorAttrEnum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

