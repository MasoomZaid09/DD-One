package org.example.internal_logcat.domain.models.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("email")
    var email: String,
    @SerialName("passwordHash")
    var passwordHash: String
)