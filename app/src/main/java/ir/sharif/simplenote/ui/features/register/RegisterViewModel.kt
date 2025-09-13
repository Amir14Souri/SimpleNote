package ir.sharif.simplenote.ui.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.model.RegisterError
import ir.sharif.simplenote.domain.model.RegisterValidationError
import ir.sharif.simplenote.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUIState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val error: String? = null,

    val isCreating: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    sealed class NavigationEvent{
        data object ToLogin: NavigationEvent()
    }
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    val navigationEvents = _navigationEvents.asSharedFlow()

    fun register() {
        if (uiState.value.isCreating) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreating = true)
            if (uiState.value.password != uiState.value.passwordConfirm) {
                _uiState.value = _uiState.value.copy(
                  passwordConfirmError = "Passwords do not match",
                  passwordError = "Passwords do not match",
                  error = null,
                  emailError = null,
                  firstNameError = null,
                  lastNameError = null,
                  usernameError = null, isCreating = false)

                return@launch
            }

            val state = uiState.value.copy()
            val res = userRepository.register(
                state.firstName,
                state.lastName,
                state.username,
                state.email,
                state.password)
            res.onSuccess {
                _navigationEvents.emit(NavigationEvent.ToLogin)
            }.onError { registerError ->
                var firstNameError: String = ""
                var lastNameError: String = ""
                var usernameError: String = ""
                var emailError: String = ""
                var passwordError: String =""
                var error: String = ""
                if (registerError is RegisterError.ValidationError) {
                    registerError.errors.forEach { err ->
                        when (err) {
                            is RegisterValidationError.FirstName -> firstNameError += err.detail
                            is RegisterValidationError.LastName -> lastNameError += err.detail
                            is RegisterValidationError.Username -> usernameError += err.detail
                            is RegisterValidationError.Email -> emailError += err.detail
                            is RegisterValidationError.Password -> passwordError += err.detail
                            is RegisterValidationError.NonField -> error += err.detail
                        }
                    }
                } else {
                    error = registerError.message.orEmpty()
                }
                _uiState.value = _uiState.value.copy(
                    firstNameError = firstNameError.ifBlank { null },
                    lastNameError = lastNameError.ifBlank { null },
                    usernameError = usernameError.ifBlank { null },
                    emailError = emailError.ifBlank { null },
                    passwordError = passwordError.ifBlank { null },
                    error = error.ifBlank { null },
                    isCreating = false,
                    )

            }
        }
    }

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun setPasswordConfirm(passwordConfirm: String) {
        _uiState.value = _uiState.value.copy(passwordConfirm = passwordConfirm)
    }

    fun setUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun setFirstName(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun setLastName(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

}