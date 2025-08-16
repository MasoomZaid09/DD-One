package org.example.internal_logcat.domain.usecases

import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.response.DeviceDataResponse
import org.example.internal_logcat.domain.models.response.FormResponse
import org.example.internal_logcat.domain.models.response.LoginResponse
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse
import org.example.internal_logcat.domain.repo.DDRepository
import org.example.internal_logcat.utils.StateClass


class DDUseCase(private val ddRepository: DDRepository) {

    suspend fun loginEvent(email:String,pass:String) : StateClass.UiState<LoginResponse> {
        return ddRepository.loginApi(email,pass)
    }

    suspend fun fetchDevicesList() : StateClass.UiState<DeviceDataResponse> {
        return ddRepository.devicesListApi()
    }

    suspend fun addNewDevice(formRequest: FormRequest) : StateClass.UiState<FormResponse> {
        return ddRepository.addFormData(formRequest)
    }

    suspend fun fetchSingleDeviceData(id:String) : StateClass.UiState<SingleDeviceResponse> {
        return ddRepository.fetchFormData(id)
    }

    suspend fun updateSingleFormData(id: String, formRequest: FormRequest) : StateClass.UiState<FormResponse> {
        return ddRepository.updateFormData(id,formRequest)
    }
}