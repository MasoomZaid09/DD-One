package org.example.internal_logcat.domain.models.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String = "",
    @SerialName("statusCode")
    val statusCode: Int = 0,
    @SerialName("statusValue")
    val statusValue: String = ""
) {
    @Serializable
    class Data(
        @SerialName("accessHospital")
        val accessHospital: ArrayList<String> = ArrayList(),
        @SerialName("contactNumber")
        val contactNumber: String = "",
        @SerialName("designation")
        val designation: String = "",
        @SerialName("email")
        val email: String = "",
        @SerialName("hospitalAddress")
        val hospitalAddress: String = "",
        @SerialName("hospitalName")
        val hospitalName: String = "",
        @SerialName("_id")
        val id: String = "",
        @SerialName("image")
        val image: String = "",
        @SerialName("isSuperAdmin")
        val isSuperAdmin: Boolean = false,
        @SerialName("name")
        val name: String = "",
        @SerialName("securityCode")
        val securityCode: String = "",
        @SerialName("speciality")
        val speciality: String = "",
        @SerialName("token")
        val token: String = "",
        @SerialName("userStatus")
        val userStatus: String = "",
        @SerialName("userType")
        val userType: String = ""
    )
}