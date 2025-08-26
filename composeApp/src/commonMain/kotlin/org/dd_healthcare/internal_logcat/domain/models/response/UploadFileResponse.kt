package org.dd_healthcare.internal_logcat.domain.models.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileResponse(
    @SerialName("file")
    val file: File? = null,
    @SerialName("message")
    val message: String = "",
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("statusValue")
    val statusValue: String = ""
)

@Serializable
data class File(
    @SerialName("mimeType")
    val mimeType: String = "",
    @SerialName("originalName")
    val originalName: String = "",
    @SerialName("path")
    val path: String = "",
    @SerialName("size")
    val size: Int = 0
)
