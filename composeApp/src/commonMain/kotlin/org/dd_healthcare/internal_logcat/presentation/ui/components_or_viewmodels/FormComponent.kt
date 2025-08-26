package org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels

import com.arkivanov.decompose.ComponentContext
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.local.SharedPreferencesImpl
import org.dd_healthcare.internal_logcat.domain.models.request.FormRequest
import org.dd_healthcare.internal_logcat.domain.models.response.FormResponse
import org.dd_healthcare.internal_logcat.domain.models.response.SingleDeviceResponse
import org.dd_healthcare.internal_logcat.domain.models.response.UploadFileResponse
import org.dd_healthcare.internal_logcat.domain.usecases.DDUseCase
import org.dd_healthcare.internal_logcat.utils.StateClass

class FormComponent(
    private val componentContext: ComponentContext,
    val navigateToDashboard: () -> Unit,
    val navigateBack: () -> Unit,
    val data: String,
    val isNewDevice: Boolean
) : ComponentContext by componentContext {

    private val ddUseCase: DDUseCase = getKoin().get()

    private val _formResponse =
        MutableStateFlow<StateClass.UiState<FormResponse>>(StateClass.UiState.Idle)
    var formResponse = _formResponse

    private val _singleDeviceResponse =
        MutableStateFlow<StateClass.UiState<SingleDeviceResponse>>(StateClass.UiState.Idle)
    var singleDeviceResponse = _singleDeviceResponse

    private val _uploadFileResponse =
        MutableStateFlow<StateClass.UiState<UploadFileResponse>>(StateClass.UiState.Idle)
    private val _uploadFileResponseSecond =
        MutableStateFlow<StateClass.UiState<UploadFileResponse>>(StateClass.UiState.Idle)
    var uploadFileResponse = _uploadFileResponse
    var uploadFileResponseSecond = _uploadFileResponseSecond


    fun uploadFileOnServer(isMultiple: Boolean, file: KmpFile, context: PlatformContext) {

        CoroutineScope(Dispatchers.IO).launch {
            if (isMultiple) _uploadFileResponse.emit(StateClass.UiState.Loading)
            else _uploadFileResponseSecond.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.uploadFileOnServer(file, context)
                if (isMultiple) _uploadFileResponse.emit(response)
                else _uploadFileResponseSecond.emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
                if (isMultiple) _uploadFileResponse.emit(StateClass.UiState.Error(e.message.toString()))
                else _uploadFileResponseSecond.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }

    fun addDeviceUsingForm(formRequest: FormRequest) {

        // i am using shared preferences to get userId and putting into formRequest
        // because formRequest is coming from different composables
        // so write is every where is not a good idea
        val sharedPreferences: SharedPreferencesImpl = getKoin().get()
        formRequest.userId = sharedPreferences.getUserId()

        CoroutineScope(Dispatchers.IO).launch {
            _formResponse.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.addNewDevice(formRequest)
                _formResponse.emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _formResponse.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }

    fun updateDeviceUsingForm(id: String, formRequest: FormRequest) {

        val sharedPreferences: SharedPreferencesImpl = getKoin().get()
        formRequest.userId = sharedPreferences.getUserId()

        CoroutineScope(Dispatchers.IO).launch {
            _formResponse.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.updateSingleFormData(id, formRequest)
                _formResponse.emit(response)
                withContext(Dispatchers.Main) {
                    navigateToDashboard()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _formResponse.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }

    // get single device details
    fun getSingleDeviceData(id: String) {

        CoroutineScope(Dispatchers.IO).launch {
            _singleDeviceResponse.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.fetchSingleDeviceData(id)
                _singleDeviceResponse.emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _singleDeviceResponse.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }


}