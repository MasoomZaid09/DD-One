package org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.models.response.DeviceDataResponse
import org.dd_healthcare.internal_logcat.domain.usecases.DDUseCase
import org.dd_healthcare.internal_logcat.utils.StateClass

class DashboardComponent(
    componentContext: ComponentContext,
    val navigateToLogin: () -> Unit,
    private val goToFormPage: (String, Boolean) -> Unit
) : ComponentContext by componentContext {


    private val ddUseCase: DDUseCase = getKoin().get()
    private val _deviceListResponse =
        MutableStateFlow<StateClass.UiState<DeviceDataResponse>>(StateClass.UiState.Idle)
    var deviceListResponse = _deviceListResponse

    fun deviceListApi() {
        CoroutineScope(Dispatchers.IO).launch {
            _deviceListResponse.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.fetchDevicesList()
                _deviceListResponse.emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _deviceListResponse.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }

    fun sendDataToFormPage(data: String, isNewDevice: Boolean) {
        goToFormPage(data, isNewDevice)
    }
}