package org.example.internal_logcat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.back_logo
import org.example.internal_logcat.presentation.ui.mini_features.AccountScreen
import org.example.internal_logcat.presentation.ui.mini_features.DispatchScreen
import org.example.internal_logcat.presentation.ui.composables.InfoLoggingFormStatus
import org.example.internal_logcat.presentation.ui.mini_features.ProductionScreen
import org.example.internal_logcat.presentation.ui.mini_features.ServiceEngineerScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import org.example.internal_logcat.domain.local.SharedPreferencesImpl
import org.example.internal_logcat.domain.models.response.AccountDepartment
import org.example.internal_logcat.domain.models.response.Data
import org.example.internal_logcat.domain.models.response.DispatchDepartment
import org.example.internal_logcat.domain.models.response.ProductionDepartment
import org.example.internal_logcat.domain.models.response.ServiceDepartment
import org.example.internal_logcat.domain.models.response.SingleDeviceResponse
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.utils.AppColors
import org.example.internal_logcat.utils.SharedLogger
import org.example.internal_logcat.utils.StateClass

@Composable
fun FormScreen(component: FormComponent) {

    val sharedPreferences: SharedPreferencesImpl = getKoin().get()
    val stateOfAddApi by component.formResponse.collectAsState()

    // new device added
    when (stateOfAddApi) {

        is StateClass.UiState.Idle -> {
            MainUI(component, sharedPreferences)
        }

        is StateClass.UiState.Loading -> {

            BoxWithConstraints(modifier = Modifier.fillMaxSize().background(AppColors.whiteColor)) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).height(maxHeight * 0.10f)
                        .width(maxWidth * 0.15f),
                    color = AppColors.themeGreenColor
                )
            }
        }

        is StateClass.UiState.Success -> {
            component.navigateToDashboard()
        }

        is StateClass.UiState.Error -> {}
    }

}

@Composable
fun MainUI(component: FormComponent, sharedPreferences: SharedPreferencesImpl) {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.whiteColor)
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {

        // top bar
        Column(
            Modifier.padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.001f))

            Image(
                painter = painterResource(Res.drawable.back_logo),
                contentDescription = "Back Icon",
                modifier = Modifier
                    .size(80.dp).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        component.navigateBack()
                    }
            )

            // when we add new device using QR code
            if (component.isNewDevice) {

                val dataFromQR = component.data.split(",")

                val responseProduction = ProductionDepartment(
                    serialNumber = dataFromQR[0],
                    ipMacAddress = dataFromQR[1],
                    androidMacAddress = dataFromQR[2],
                    deviceId = dataFromQR[3],
                    softwareVersion = dataFromQR[4],
                    description = "",
                    dhrFile = "",
                    model = "Agvac Pro Universal",
                    neoSensorType = "Reusable",
                    exhaleValveType = "Reusable",
                    screenSize = "15 Inch",
                    type = "Demo"
                )

                InfoLoggingFormStatus(1)

                Spacer(modifier = Modifier.height(20.dp))

                when (sharedPreferences.getUserType()) {

                    "Production" -> {
                        ProductionScreen(component, responseProduction, true, Data())
                    }

                    "Accounts" -> {
                        AccountScreen(component, AccountDepartment(), true, Data())
                    }

                    "Dispatch" -> {
                        DispatchScreen(component, DispatchDepartment(), true, Data())
                    }

                    "Support" -> {
                        ServiceEngineerScreen(component, ServiceDepartment(), true, Data())
                    }
                }
            }
            // now if we edit already exist device
            else {

                LaunchedEffect(Unit) {
                    component.getSingleDeviceData(component.data)
                }

                val singleDeviceResponse by component.singleDeviceResponse.collectAsState()

                when (singleDeviceResponse) {
                    is StateClass.UiState.Loading -> {
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                                .background(AppColors.whiteColor)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                                    .height(maxHeight * 0.10f)
                                    .width(maxWidth * 0.20f),
                                color = AppColors.themeGreenColor
                            )
                        }
                    }

                    is StateClass.UiState.Idle -> {}
                    is StateClass.UiState.Success -> {
                        val response = (singleDeviceResponse as StateClass.UiState.Success<SingleDeviceResponse>).data.data

                        var statusNumber = 0
                        if (response.productionDepartment.isNotEmpty()){
                            statusNumber++
                        }
                        if (response.accountDepartment.isNotEmpty()){
                            statusNumber++
                        }
                        if (response.dispatchDepartment.isNotEmpty()){
                            statusNumber++
                        }
                        if (response.serviceDepartment.isNotEmpty()){
                            statusNumber++
                        }

                        SharedLogger.i("StatusNumber : $statusNumber | Response : $response")
                        InfoLoggingFormStatus(statusNumber)

                        Spacer(modifier = Modifier.height(20.dp))

                        when (sharedPreferences.getUserType()) {

                            "Production" -> {
                                ProductionScreen(
                                    component,
                                    response.productionDepartment[0],
                                    false,
                                    response
                                )
                            }

                            "Accounts" -> {
                                AccountScreen(
                                    component,
                                    if (response.accountDepartment.isEmpty()) AccountDepartment() else response.accountDepartment[0],
                                    false,
                                    response
                                )
                            }

                            "Dispatch" -> {
                                DispatchScreen(
                                    component,
                                    if (response.dispatchDepartment.isEmpty()) DispatchDepartment() else response.dispatchDepartment[0],
                                    false,
                                    response
                                )
                            }

                            "Support" -> {
                                ServiceEngineerScreen(
                                    component,
                                    if (response.serviceDepartment.isEmpty()) ServiceDepartment() else response.serviceDepartment[0],
                                    false,
                                    response
                                )
                            }
                        }
                    }

                    is StateClass.UiState.Error -> {}
                }
            }

        }
    }
}