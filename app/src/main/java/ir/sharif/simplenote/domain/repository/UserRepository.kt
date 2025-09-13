package ir.sharif.simplenote.domain.repository

import ir.sharif.simplenote.domain.model.ChangePasswordError
import ir.sharif.simplenote.domain.model.LoginError
import ir.sharif.simplenote.domain.model.RegisterError
import ir.sharif.simplenote.domain.model.User
import ir.sharif.simplenote.util.Resource


interface UserRepository {
    suspend fun register(firstName: String, lastName: String, username: String,
                 email: String, password: String): Resource<Unit, RegisterError>
    suspend fun login(username: String, password: String): Resource<User, LoginError>

    suspend fun changePassword(oldPassword: String, newPassword: String): Resource<Unit, ChangePasswordError>

    suspend fun refreshToken(): Resource<String, String>

    suspend fun finishSync()

    suspend fun logout()

    suspend fun getUser(): Result<User>
}