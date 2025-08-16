package org.example.internal_logcat.data.repo

import org.example.internal_logcat.data.remote.DDApi
import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.response.DeviceDataResponse
import org.example.internal_logcat.domain.models.response.FormResponse
import org.example.internal_logcat.domain.models.response.LoginResponse
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse
import org.example.internal_logcat.domain.repo.DDRepository
import org.example.internal_logcat.utils.StateClass

// here we write business login or filter on api
class DDRepositoryImpl(private val ddApi: DDApi) : DDRepository {

    override suspend fun loginApi(email: String, pass: String): StateClass.UiState<LoginResponse> {

        val response = ddApi.loginApi(email, pass)
        return if (response.statusCode == 200) {
            StateClass.UiState.Success(response)
        } else {
            StateClass.UiState.Error(response.message)
        }
    }

    override suspend fun devicesListApi(): StateClass.UiState<DeviceDataResponse> {
        val response = ddApi.devicesListApi()
        return if (response.statusCode == 200) {
            StateClass.UiState.Success(response)
        } else {
            StateClass.UiState.Error(response.message)
        }
    }

    override suspend fun addFormData(formRequest: FormRequest): StateClass.UiState<FormResponse> {
        val response = ddApi.addFormData(formRequest)
        return if (response.statusCode == 201) {
            StateClass.UiState.Success(response)
        } else {
            StateClass.UiState.Error(response.message)
        }
    }

    override suspend fun fetchFormData(id: String): StateClass.UiState<SingleDeviceResponse> {
        val response = ddApi.fetchFormData(id)
        return if (response.statusCode == 200) {
            StateClass.UiState.Success(response)
        } else {
            StateClass.UiState.Error(response.message)
        }
    }

    override suspend fun updateFormData(
        id: String,
        formRequest: FormRequest
    ): StateClass.UiState<FormResponse> {
        val response = ddApi.updateFormData(id, formRequest)
        return if (response.statusCode == 2000) {
            StateClass.UiState.Success(response)
        } else {
            StateClass.UiState.Error(response.message)
        }
    }
}