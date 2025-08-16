package org.example.internal_logcat.domain.repo

import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.response.DeviceDataResponse
import org.example.internal_logcat.domain.models.response.FormResponse
import org.example.internal_logcat.domain.models.response.LoginResponse
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse
import org.example.internal_logcat.utils.StateClass

interface DDRepository {

    suspend fun loginApi(email:String,pass:String) : StateClass.UiState<LoginResponse>
    suspend fun devicesListApi() : StateClass.UiState<DeviceDataResponse>
    suspend fun addFormData(formRequest: FormRequest) : StateClass.UiState<FormResponse>
    suspend fun fetchFormData(id:String) : StateClass.UiState<SingleDeviceResponse>
    suspend fun updateFormData(id:String,formRequest: FormRequest) : StateClass.UiState<FormResponse>
}