package ir.sharif.simplenote.client.infrastructure

import ir.sharif.simplenote.client.auth.HttpBearerAuth

import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.CallAdapter
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient(
    private var baseUrl: String = defaultBasePath,
    private val okHttpClientBuilder: OkHttpClient.Builder? = null,
    private val serializerBuilder: GsonBuilder = registerTypeAdapterFactoryForAllModels(Serializer.gsonBuilder),
    private val callFactory: Call.Factory? = null,
    private val callAdapterFactories: List<CallAdapter.Factory> = listOf(
    ),
    private val converterFactories: List<Converter.Factory> = listOf(
        ScalarsConverterFactory.create(),
        GsonConverterFactory.create(serializerBuilder.create()),
    )
) {
    private val apiAuthorizations = mutableMapOf<String, Interceptor>()
    var logger: ((String) -> Unit)? = null

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .apply {
                callAdapterFactories.forEach {
                    addCallAdapterFactory(it)
                }
            }
            .apply {
                converterFactories.forEach {
                    addConverterFactory(it)
                }
            }
    }

    private val clientBuilder: OkHttpClient.Builder by lazy {
        okHttpClientBuilder ?: defaultClientBuilder
    }

    private val defaultClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor { message -> logger?.invoke(message) }
                .apply { level = HttpLoggingInterceptor.Level.BODY }
            )
    }

    init {
        normalizeBaseUrl()
    }

    constructor(
        baseUrl: String = defaultBasePath,
        okHttpClientBuilder: OkHttpClient.Builder? = null,
        serializerBuilder: GsonBuilder = Serializer.gsonBuilder,
        authNames: Array<String>
    ) : this(baseUrl, okHttpClientBuilder, serializerBuilder) {
        authNames.forEach { authName ->
            val auth: Interceptor? = when (authName) { 
                "jwtAuth" -> HttpBearerAuth("bearer")
                
                else -> throw RuntimeException("auth name $authName not found in available auth names")
            }
            if (auth != null) {
                addAuthorization(authName, auth)
            }
        }
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ChangePasswordRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error401.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error403.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error404.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error405.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error406.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error415.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error500.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse401.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse403.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse404.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse405.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse406.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse415.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse500.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Message.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Note.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NoteRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateErrorResponse400.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationItemError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PaginatedNoteList.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseError.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseErrorResponse.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PatchedNoteRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Register.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.RegisterRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPair.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPairRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefresh.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefreshRequest.CustomTypeAdapterFactory())
        serializerBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.UserInfo.CustomTypeAdapterFactory())
    }

    constructor(
        baseUrl: String = defaultBasePath,
        okHttpClientBuilder: OkHttpClient.Builder? = null,
        serializerBuilder: GsonBuilder = Serializer.gsonBuilder,
        authName: String,
        bearerToken: String
    ) : this(baseUrl, okHttpClientBuilder, serializerBuilder, arrayOf(authName)) {
        setBearerToken(bearerToken)
    }

    fun setBearerToken(bearerToken: String): ApiClient {
        apiAuthorizations.values.runOnFirst<Interceptor, HttpBearerAuth> {
            this.bearerToken = bearerToken
        }
        return this
    }

    /**
     * Adds an authorization to be used by the client
     * @param authName Authentication name
     * @param authorization Authorization interceptor
     * @return ApiClient
     */
    fun addAuthorization(authName: String, authorization: Interceptor): ApiClient {
        if (apiAuthorizations.containsKey(authName)) {
            throw RuntimeException("auth name $authName already in api authorizations")
        }
        apiAuthorizations[authName] = authorization
        clientBuilder.addInterceptor(authorization)
        return this
    }

    fun setLogger(logger: (String) -> Unit): ApiClient {
        this.logger = logger
        return this
    }

    fun <S> createService(serviceClass: Class<S>): S {
        val usedCallFactory = this.callFactory ?: clientBuilder.build()
        return retrofitBuilder.callFactory(usedCallFactory).build().create(serviceClass)
    }

    /**
     * Gets the serializer builder.
     * @return serial builder
     */
    fun getSerializerBuilder(): GsonBuilder {
        return serializerBuilder
    }

    private fun normalizeBaseUrl() {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }
    }

    private inline fun <T, reified U> Iterable<T>.runOnFirst(callback: U.() -> Unit) {
        for (element in this) {
            if (element is U) {
                callback.invoke(element)
                break
            }
        }
    }

    companion object {
    @JvmStatic
    protected val baseUrlKey: String = "ir.sharif.simplenote.client.baseUrl"

        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(baseUrlKey, "http://localhost")
        }
    }
}

/**
 * Registers all models with the type adapter factory.
 *
 * @param gsonBuilder gson builder
 * @return GSON builder
 */
fun registerTypeAdapterFactoryForAllModels(gsonBuilder: GsonBuilder): GsonBuilder {
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthChangePasswordCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthRegisterCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.AuthTokenRefreshCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ChangePasswordRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error401.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error403.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error404.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error405.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error406.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error415.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Error500.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse401.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse403.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse404.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse405.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse406.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse415.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ErrorResponse500.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Message.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Note.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NoteRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesBulkCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesCreateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesFilterListValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesPartialUpdateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateErrorResponse400.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.NotesUpdateValidationItemError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PaginatedNoteList.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseError.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.ParseErrorResponse.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.PatchedNoteRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.Register.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.RegisterRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPair.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenObtainPairRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefresh.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.TokenRefreshRequest.CustomTypeAdapterFactory())
    gsonBuilder.registerTypeAdapterFactory(ir.sharif.simplenote.data.remote.model.UserInfo.CustomTypeAdapterFactory())
    return gsonBuilder
}
