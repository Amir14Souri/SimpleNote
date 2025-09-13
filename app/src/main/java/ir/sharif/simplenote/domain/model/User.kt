package ir.sharif.simplenote.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User (val firstName: String?, val lastName: String?,
                  val username: String, val email: String)