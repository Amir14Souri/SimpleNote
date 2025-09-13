package ir.sharif.simplenote.data.remote.client

import ir.sharif.simplenote.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.google.gson.annotations.SerializedName

import ir.sharif.simplenote.data.remote.model.ErrorResponse401
import ir.sharif.simplenote.data.remote.model.ErrorResponse405
import ir.sharif.simplenote.data.remote.model.ErrorResponse406
import ir.sharif.simplenote.data.remote.model.ErrorResponse415
import ir.sharif.simplenote.data.remote.model.ErrorResponse500
import ir.sharif.simplenote.data.remote.model.ParseErrorResponse

interface SchemaApi {

    /**
    * enum for parameter format
    */
    enum class FormatSchemaRetrieve(val value: kotlin.String) {
        @SerializedName(value = "json") JSON("json"),
        @SerializedName(value = "yaml") YAML("yaml")
    }


    /**
    * enum for parameter lang
    */
    enum class LangSchemaRetrieve(val value: kotlin.String) {
        @SerializedName(value = "af") AF("af"),
        @SerializedName(value = "ar") AR("ar"),
        @SerializedName(value = "ar-dz") AR_MINUS_DZ("ar-dz"),
        @SerializedName(value = "ast") AST("ast"),
        @SerializedName(value = "az") AZ("az"),
        @SerializedName(value = "be") BE("be"),
        @SerializedName(value = "bg") BG("bg"),
        @SerializedName(value = "bn") BN("bn"),
        @SerializedName(value = "br") BR("br"),
        @SerializedName(value = "bs") BS("bs"),
        @SerializedName(value = "ca") CA("ca"),
        @SerializedName(value = "ckb") CKB("ckb"),
        @SerializedName(value = "cs") CS("cs"),
        @SerializedName(value = "cy") CY("cy"),
        @SerializedName(value = "da") DA("da"),
        @SerializedName(value = "de") DE("de"),
        @SerializedName(value = "dsb") DSB("dsb"),
        @SerializedName(value = "el") EL("el"),
        @SerializedName(value = "en") EN("en"),
        @SerializedName(value = "en-au") EN_MINUS_AU("en-au"),
        @SerializedName(value = "en-gb") EN_MINUS_GB("en-gb"),
        @SerializedName(value = "eo") EO("eo"),
        @SerializedName(value = "es") ES("es"),
        @SerializedName(value = "es-ar") ES_MINUS_AR("es-ar"),
        @SerializedName(value = "es-co") ES_MINUS_CO("es-co"),
        @SerializedName(value = "es-mx") ES_MINUS_MX("es-mx"),
        @SerializedName(value = "es-ni") ES_MINUS_NI("es-ni"),
        @SerializedName(value = "es-ve") ES_MINUS_VE("es-ve"),
        @SerializedName(value = "et") ET("et"),
        @SerializedName(value = "eu") EU("eu"),
        @SerializedName(value = "fa") FA("fa"),
        @SerializedName(value = "fi") FI("fi"),
        @SerializedName(value = "fr") FR("fr"),
        @SerializedName(value = "fy") FY("fy"),
        @SerializedName(value = "ga") GA("ga"),
        @SerializedName(value = "gd") GD("gd"),
        @SerializedName(value = "gl") GL("gl"),
        @SerializedName(value = "he") HE("he"),
        @SerializedName(value = "hi") HI("hi"),
        @SerializedName(value = "hr") HR("hr"),
        @SerializedName(value = "hsb") HSB("hsb"),
        @SerializedName(value = "hu") HU("hu"),
        @SerializedName(value = "hy") HY("hy"),
        @SerializedName(value = "ia") IA("ia"),
        @SerializedName(value = "id") ID("id"),
        @SerializedName(value = "ig") IG("ig"),
        @SerializedName(value = "io") IO("io"),
        @SerializedName(value = "is") IS("is"),
        @SerializedName(value = "it") IT("it"),
        @SerializedName(value = "ja") JA("ja"),
        @SerializedName(value = "ka") KA("ka"),
        @SerializedName(value = "kab") KAB("kab"),
        @SerializedName(value = "kk") KK("kk"),
        @SerializedName(value = "km") KM("km"),
        @SerializedName(value = "kn") KN("kn"),
        @SerializedName(value = "ko") KO("ko"),
        @SerializedName(value = "ky") KY("ky"),
        @SerializedName(value = "lb") LB("lb"),
        @SerializedName(value = "lt") LT("lt"),
        @SerializedName(value = "lv") LV("lv"),
        @SerializedName(value = "mk") MK("mk"),
        @SerializedName(value = "ml") ML("ml"),
        @SerializedName(value = "mn") MN("mn"),
        @SerializedName(value = "mr") MR("mr"),
        @SerializedName(value = "ms") MS("ms"),
        @SerializedName(value = "my") MY("my"),
        @SerializedName(value = "nb") NB("nb"),
        @SerializedName(value = "ne") NE("ne"),
        @SerializedName(value = "nl") NL("nl"),
        @SerializedName(value = "nn") NN("nn"),
        @SerializedName(value = "os") OS("os"),
        @SerializedName(value = "pa") PA("pa"),
        @SerializedName(value = "pl") PL("pl"),
        @SerializedName(value = "pt") PT("pt"),
        @SerializedName(value = "pt-br") PT_MINUS_BR("pt-br"),
        @SerializedName(value = "ro") RO("ro"),
        @SerializedName(value = "ru") RU("ru"),
        @SerializedName(value = "sk") SK("sk"),
        @SerializedName(value = "sl") SL("sl"),
        @SerializedName(value = "sq") SQ("sq"),
        @SerializedName(value = "sr") SR("sr"),
        @SerializedName(value = "sr-latn") SR_MINUS_LATN("sr-latn"),
        @SerializedName(value = "sv") SV("sv"),
        @SerializedName(value = "sw") SW("sw"),
        @SerializedName(value = "ta") TA("ta"),
        @SerializedName(value = "te") TE("te"),
        @SerializedName(value = "tg") TG("tg"),
        @SerializedName(value = "th") TH("th"),
        @SerializedName(value = "tk") TK("tk"),
        @SerializedName(value = "tr") TR("tr"),
        @SerializedName(value = "tt") TT("tt"),
        @SerializedName(value = "udm") UDM("udm"),
        @SerializedName(value = "uk") UK("uk"),
        @SerializedName(value = "ur") UR("ur"),
        @SerializedName(value = "uz") UZ("uz"),
        @SerializedName(value = "vi") VI("vi"),
        @SerializedName(value = "zh-hans") ZH_MINUS_HANS("zh-hans"),
        @SerializedName(value = "zh-hant") ZH_MINUS_HANT("zh-hant")
    }

    @GET("api/schema/")
    suspend fun schemaRetrieve(@Query("format") format: FormatSchemaRetrieve? = null, @Query("lang") lang: LangSchemaRetrieve? = null): Response<kotlin.collections.Map<kotlin.String, kotlin.Any>>

}
