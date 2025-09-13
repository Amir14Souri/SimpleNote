package ir.sharif.simplenote.ui.features.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.model.LoginError
import ir.sharif.simplenote.domain.model.LoginValidationError
import ir.sharif.simplenote.domain.repository.NoteRepository
import ir.sharif.simplenote.domain.repository.UserRepository
import ir.sharif.simplenote.ui.features.login.RegisterUIState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

enum class SyncStatus {
    IN_PROGRESS,
    FAILED,
    FINISHED
}

data class SyncUIState(val status: SyncStatus)

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    val logger: Logger = Logger.getLogger("SyncViewModel")

    private val _uiState = MutableStateFlow(SyncUIState(SyncStatus.IN_PROGRESS))
    val uiState = _uiState.asStateFlow()

    fun startSync() {
        viewModelScope.launch {
            delay(3000) // for better ux
             noteRepository.syncAll().onSuccess {
                _uiState.value = _uiState.value.copy(
                    status = SyncStatus.FINISHED
                )
                userRepository.finishSync()
            }.onFailure { _ -> _uiState.value = _uiState.value.copy(status = SyncStatus.FAILED)}
        }
    }

    fun onAcknowledgeSyncFailureClicked() {
        viewModelScope.launch {
            userRepository.finishSync()
        }
    }

}
