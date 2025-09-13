package ir.sharif.simplenote.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.sharif.simplenote.domain.model.Token
import ir.sharif.simplenote.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

sealed interface AuthStatus {
    data object Loading : AuthStatus
    data class Authenticated(val token: Token, val user: User?) : AuthStatus

    data class Syncing(val token: Token, val user: User?): AuthStatus
    data object Unauthenticated : AuthStatus
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val json = Json { ignoreUnknownKeys = true }
    private val logger: Logger = Logger.getLogger("SessionManager")
    private val dataStore = context.dataStore

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val lock = Mutex()

    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Loading)
    val authStatus: StateFlow<AuthStatus> = _authStatus.asStateFlow()

    private var tokenCache: Token? = null
    private var userCache: User? = null

    init {
        scope.launch {
            lock.withLock {
                val tokenJsonString = dataStore.data.map { it[TOKEN_KEY] }.first()
                val userJsonString = dataStore.data.map { it[USER_PROFILE_KEY] }.first()

                tokenCache = tokenJsonString?.let { deserializeToken(it) }
                userCache = userJsonString?.let { deserializeUser(it) }

                if (tokenCache != null) {
                    _authStatus.value = AuthStatus.Authenticated(tokenCache!!, userCache)
                } else {
                    _authStatus.value = AuthStatus.Unauthenticated
                }
            }
        }
    }

    suspend fun saveToken(token: Token) {
        lock.withLock {
            dataStore.edit { prefs ->
                prefs[TOKEN_KEY] = json.encodeToString(token)
            }
            tokenCache = token
            _authStatus.value = AuthStatus.Authenticated(token, userCache)
        }
    }

    suspend fun saveUserProfile(user: User) {
        lock.withLock {
            dataStore.edit { prefs ->
                prefs[USER_PROFILE_KEY] = json.encodeToString(user)
            }

            tokenCache?.let {
                if (userCache == null )  {
                    _authStatus.value = AuthStatus.Syncing(it, user)
                } else {
                    _authStatus.value = AuthStatus.Authenticated(it, user)
                }
            }

            userCache = user
        }
    }


    suspend fun finishedSync() {
        lock.withLock {
            _authStatus.value = AuthStatus.Authenticated(tokenCache!!, userCache)
        }
    }

    suspend fun clearSession() {
        lock.withLock {
            dataStore.edit { it.clear() }
            tokenCache = null
            userCache = null
            _authStatus.value = AuthStatus.Unauthenticated
        }
    }

    fun getAccessToken(): String? = tokenCache?.accessToken
    fun getRefreshToken(): String? = tokenCache?.refreshToken

    private fun deserializeToken(jsonString: String): Token? {
        return try {
            json.decodeFromString<Token>(jsonString)
        } catch (e: Exception) {
            logger.warning("Error deserializing token: ${e.message}")
            null
        }
    }

    private fun deserializeUser(jsonString: String): User? {
        return try {
            json.decodeFromString<User>(jsonString)
        } catch (e: Exception) {
            logger.warning("Error deserializing user profile: ${e.message}")
            null
        }
    }

    fun getUserProfile(): User? = userCache

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_PROFILE_KEY = stringPreferencesKey("user_profile_json")
    }
}