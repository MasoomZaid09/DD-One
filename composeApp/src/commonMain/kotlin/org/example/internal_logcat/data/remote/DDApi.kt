package org.example.internal_logcat.data.remote

import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.response.DeviceDataResponse
import org.example.internal_logcat.domain.models.response.FormResponse
import org.example.internal_logcat.domain.models.response.LoginResponse
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse

interface DDApi {

    suspend fun loginApi(email:String, password:String) : LoginResponse
    suspend fun devicesListApi() : DeviceDataResponse
    suspend fun addFormData(formRequest: FormRequest) : FormResponse
    suspend fun fetchFormData(id:String) : SingleDeviceResponse
    suspend fun updateFormData(id:String,formRequest: FormRequest) : FormResponse
}