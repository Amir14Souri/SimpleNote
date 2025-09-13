@file:OptIn(ExperimentalTime::class)

package ir.sharif.simplenote.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.json.Json
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

val Context.syncDataStore: DataStore<Preferences> by preferencesDataStore(name = "sync")

@Singleton
class SyncManager @Inject constructor(@ApplicationContext context: Context) {
    private val json = Json { ignoreUnknownKeys = true }
    private val logger: Logger = Logger.getLogger("SyncManager")
    private val dataStore = context.syncDataStore

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val lock = Mutex()

    suspend fun setLastSyncNow() {
        dataStore.edit { prefs ->
            prefs[LAST_SYNC_KEY] = Clock.System.now().epochSeconds.toString()
        }
    }

    suspend fun getLastSync(): Instant? {
        return dataStore.data.map{
            val lastSync = it[LAST_SYNC_KEY]?.toLong() ?: return@map null
            Instant.fromEpochSeconds(lastSync)
        }.first()
    }


    companion object {
        private val LAST_SYNC_KEY = stringPreferencesKey("last_sync")
    }
}