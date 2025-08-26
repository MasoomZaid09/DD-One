package org.dd_healthcare.internal_logcat.domain.models.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormResponse(
    @SerialName("message")
    val message: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("statusValue")
    val statusValue: String
)