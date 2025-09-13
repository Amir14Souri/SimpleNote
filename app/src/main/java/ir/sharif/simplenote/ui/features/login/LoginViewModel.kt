package ir.sharif.simplenote.ui.features.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sharif.simplenote.domain.model.LoginError
import ir.sharif.simplenote.domain.model.LoginValidationError
import ir.sharif.simplenote.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class NavigationDestination {
    SYNC,
    REGISTER
}
data class RegisterUIState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val error: String? = null,

    val isLoggingIn: Boolean = false,

    val navigationDestination: NavigationDestination? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState = _uiState.asStateFlow()


    fun login() {
        if (uiState.value.isLoggingIn) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoggingIn = true)

            val state = uiState.value.copy()
            val res = userRepository.login(state.username, state.password)

            res.onSuccess {
                _uiState.value = _uiState.value.copy(navigationDestination = NavigationDestination.SYNC)
                return@launch
            }.onError { err ->

                var usernameError: String = ""
                var passwordError: String = ""
                var error: String = ""

                when(err) {
                    is LoginError.ValidationError -> {

                        err.errors.map {
                            when(it) {
                                is LoginValidationError.Username -> usernameError += it.detail
                                is LoginValidationError.Password -> passwordError += it.detail
                                is LoginValidationError.NonField -> error += it.detail
                            }
                        }
                    }
                    is LoginError.ClientError -> {
                        error = err.detail
                    }
                    is LoginError.AuthError -> {
                        error = "invalid username or password"
                    }
                }

                _uiState.value = _uiState.value.copy(
                    usernameError = usernameError.ifBlank { null },
                    passwordError = passwordError.ifBlank { null },
                    error = error.ifBlank { null },
                    isLoggingIn = false,
                )
            }.onFailure { message, _ ->
                _uiState.value = _uiState.value.copy(
                    error = message,
                    isLoggingIn = false,
                )
            }
        }
    }


    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }


    fun setUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun goToRegisterClicked() {
        _uiState.value = _uiState.value.copy(navigationDestination = NavigationDestination.REGISTER)
    }

    fun consumeNavigationDestination() {
        _uiState.value = _uiState.value.copy(navigationDestination = null)
    }


}