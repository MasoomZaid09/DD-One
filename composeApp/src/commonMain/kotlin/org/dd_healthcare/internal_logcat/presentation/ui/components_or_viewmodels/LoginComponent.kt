package org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.models.response.LoginResponse
import org.dd_healthcare.internal_logcat.domain.usecases.DDUseCase
import org.dd_healthcare.internal_logcat.utils.StateClass

class LoginComponent(componentContext: ComponentContext, val navigateToDashboard : () -> Unit) : ComponentContext by componentContext {

    private val ddUseCase : DDUseCase = getKoin().get()

    private val _loginResponse = MutableStateFlow<StateClass.UiState<LoginResponse>>(StateClass.UiState.Idle)
    var loginResponse = _loginResponse

    fun loginApi(email:String,password:String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loginResponse.emit(StateClass.UiState.Loading)

            try {
                val response = ddUseCase.loginEvent(email,password)
                _loginResponse.emit(response)
            }catch (e:Exception){
                e.printStackTrace()
                _loginResponse.emit(StateClass.UiState.Error(e.message.toString()))
            }
        }
    }

}