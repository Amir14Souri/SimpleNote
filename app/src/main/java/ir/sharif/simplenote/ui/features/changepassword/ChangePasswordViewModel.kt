package ir.sharif.simplenote.ui.features.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.model.ChangePasswordError
import ir.sharif.simplenote.domain.model.ChangePasswordValidationError
import ir.sharif.simplenote.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class BottomNotificationModalSignal(
    val title: String,
    val content: String,
)


data class ChangePasswordUIStates (
    val currentPassword: String="",
    val newPassword: String="",
    val newPasswordRetype: String="",
    val currentPasswordError: String? = null,
    val newPasswordError: String? = null,
    val newPasswordRetypeError: String? = null,

    val showModal: Boolean = false,
    val notificationTitle: String = "",
    val notificationContent: String = ""
)

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {
    private val _uiStates = MutableStateFlow(ChangePasswordUIStates())
    val uiStates = _uiStates.asStateFlow()


    fun setCurrentPassword (currentPassword: String) {
        _uiStates.value = _uiStates.value.copy(
            currentPassword = currentPassword
        )
    }
    fun setNewPassword (newPassword: String) {
        _uiStates.value = _uiStates.value.copy(
            newPassword = newPassword
        )
    }
    fun setNewPasswordRetype (newPasswordRetype: String) {
        _uiStates.value = _uiStates.value.copy(
            newPasswordRetype = newPasswordRetype
        )
    }

    fun setShowModal(showModal: Boolean) {
        _uiStates.value = _uiStates.value.copy(
            showModal = showModal
        )
    }

    fun changePassword() {
        val states = _uiStates.value
        viewModelScope.launch {
            _uiStates.value = _uiStates.value.copy(
                newPasswordError = null,
                currentPasswordError = null,
                newPasswordRetypeError = null
            )
            if (states.newPassword != states.newPasswordRetype) {
                _uiStates.value = _uiStates.value.copy(
                    newPasswordError = "New passwords does not match",
                    newPasswordRetypeError = "New passwords does not match"
                )
                return@launch
            }
            val response = userRepository.changePassword(
                states.currentPassword,
                states.newPassword
            )
            response.onSuccess {
                _uiStates.value = _uiStates.value.copy(
                    showModal = true,
                    notificationTitle = "Password Changed",
                    notificationContent = "Your password has been changed successfully"
                )
            }.onError { err ->
                when(err) {
                    is ChangePasswordError.ValidationError -> {
                        err.errors.forEach {
                            validationError ->
                            when(validationError) {
                                is ChangePasswordValidationError.OldPassword -> {
                                    _uiStates.value = _uiStates.value.copy(
                                        currentPasswordError = validationError.detail
                                    )
                                }
                                is ChangePasswordValidationError.NewPassword -> {
                                    _uiStates.value = _uiStates.value.copy(
                                        newPasswordError = validationError.detail,
                                        newPasswordRetypeError = validationError.detail
                                    )
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }.onFailure { message, _ ->

            }
        }
    }
}
