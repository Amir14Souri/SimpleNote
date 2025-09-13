package ir.sharif.simplenote.ui.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SettingsUiStates(
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",
    val isLogOutDialogVisible: Boolean = false,
    val isLogOutLoading: Boolean = false,
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    val userRepository: UserRepository,
): ViewModel() {
    sealed class NavigationEvent {
        data object ToLogin: NavigationEvent()
    }
    private val _uiStates = MutableStateFlow(SettingsUiStates())
    val uiStates = _uiStates.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvents.asSharedFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().onSuccess {
                _uiStates.value = _uiStates.value.copy(
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email
                )
            }
        }
    }

    fun setIsLogOutDialogVisible(visibility: Boolean) {
        if (!visibility) {
            if (_uiStates.value.isLogOutLoading) {
                return
            }
        }
        _uiStates.value = _uiStates.value.copy(
            isLogOutDialogVisible = visibility
        )
    }

    fun logout() {
        if (_uiStates.value.isLogOutLoading) {
            return
        }

        _uiStates.value = _uiStates.value.copy(
            isLogOutLoading = true,
        )

        viewModelScope.launch {
            userRepository.logout()
            _uiStates.value = _uiStates.value.copy(
                isLogOutDialogVisible = false,
            )
            _navigationEvents.emit(NavigationEvent.ToLogin)
        }
    }
}