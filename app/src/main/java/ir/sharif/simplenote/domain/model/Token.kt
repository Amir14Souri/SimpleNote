package ir.sharif.simplenote.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Token(val accessToken: String, val refreshToken: String, val expiresIn: LocalDateTime)
