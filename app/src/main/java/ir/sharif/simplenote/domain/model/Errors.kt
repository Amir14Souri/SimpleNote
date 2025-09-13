package ir.sharif.simplenote.domain.model


sealed class ChangePasswordValidationError {
    data class OldPassword(val detail: String): ChangePasswordValidationError()
    data class NewPassword(val detail: String): ChangePasswordValidationError()
    data class NoneField(val detail: String): ChangePasswordValidationError()
}

sealed class ChangePasswordError: Exception() {
    data class ValidationError(val errors: List<ChangePasswordValidationError>): ChangePasswordError()
}

sealed class RegisterValidationError {

    data class FirstName(val detail: String): RegisterValidationError()
    data class LastName(val detail: String): RegisterValidationError()
    data class Username(val detail: String): RegisterValidationError()
    data class Email(val detail: String): RegisterValidationError()
    data class Password(val detail: String): RegisterValidationError()
    data class NonField(val detail: String): RegisterValidationError()
}

sealed class RegisterError: Exception() {
    data class ValidationError(val errors: List<RegisterValidationError>): RegisterError()
    data class ClientError(val detail: String): RegisterError()

}

sealed class LoginValidationError {
    data class Username(val detail: String): LoginValidationError()
    data class Password(val detail: String): LoginValidationError()
    data class NonField(val detail: String): LoginValidationError()
}

sealed class LoginError: Exception() {
    data class ValidationError(val errors: List<LoginValidationError>): LoginError()
    data class ClientError(val detail: String): LoginError()
    data class AuthError(val code: String): LoginError()
}
