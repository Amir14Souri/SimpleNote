package ir.sharif.simplenote.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ir.sharif.simplenote.data.network.NetworkConnectivityManager
import ir.sharif.simplenote.domain.repository.NoteRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val noteRepository: NoteRepository,
    private val connectivityManager: NetworkConnectivityManager
) : CoroutineWorker(context, workerParams) {

   private val logger = java.util.logging.Logger.getLogger("sync_worker")

    override suspend fun doWork(): Result {
        if (!connectivityManager.isConnected.value) {
            return Result.retry()
        }

        noteRepository.sync().onSuccess {
            return Result.success()
        }.onFailure {
            return Result.failure()
        }
        return Result.failure()
    }
}

