package org.example.internal_logcat.domain.models.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleDeviceResponse(
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("statusValue")
    val statusValue: String
)

