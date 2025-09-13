@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ir.sharif.simplenote.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * * `title` - title * `description` - description * `updated__lte` - updated__lte * `updated__gte` - updated__gte
 *
 * Values: TITLE,DESCRIPTION,UPDATED__LTE,UPDATED__GTE
 */

enum class NotesFilterListValidationItemErrorAttrEnum(val value: kotlin.String) {

    @SerializedName(value = "title")
    TITLE("title"),

    @SerializedName(value = "description")
    DESCRIPTION("description"),

    @SerializedName(value = "updated__lte")
    UPDATED__LTE("updated__lte"),

    @SerializedName(value = "updated__gte")
    UPDATED__GTE("updated__gte");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is NotesFilterListValidationItemErrorAttrEnum) "$data" else null

        /**
         * Returns a valid [NotesFilterListValidationItemErrorAttrEnum] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): NotesFilterListValidationItemErrorAttrEnum? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

