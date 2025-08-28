package org.dd_healthcare.internal_logcat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.back_logo
import network.chaintech.kmp_date_time_picker.getPlatform
import org.dd_healthcare.internal_logcat.presentation.ui.mini_features.AccountScreen
import org.dd_healthcare.internal_logcat.presentation.ui.mini_features.DispatchScreen
import org.dd_healthcare.internal_logcat.presentation.ui.composables.InfoLoggingFormStatus
import org.dd_healthcare.internal_logcat.presentation.ui.mini_features.ProductionScreen
import org.dd_healthcare.internal_logcat.presentation.ui.mini_features.ServiceEngineerScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.local.SharedPreferencesImpl
import org.dd_healthcare.internal_logcat.domain.models.response.AccountDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.Data
import org.dd_healthcare.internal_logcat.domain.models.response.DispatchDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.ProductionDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.ServiceDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.SingleDeviceResponse
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.SharedLogger
import org.dd_healthcare.internal_logcat.utils.StateClass

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
    val focusManager = LocalFocusManager.current
    BoxWithConstraints(
        modifier = if (getPlatform().name.contains("iOS")) Modifier.fillMaxSize()
            .background(AppColors.whiteColor)
            .pointerInput(Unit){
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
        else Modifier.fillMaxSize()
            .background(AppColors.whiteColor)
            .pointerInput(Unit){
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
                // dont use it for ios because it can generate UI shrink or resize issue when keyboard open
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        val maxHeight = maxHeight
        val maxWidth = maxWidth

        // top bar
        Column(
            Modifier.padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(maxHeight * 0.01f))

            Image(
                painter = painterResource(Res.drawable.back_logo),
                contentDescription = "Back Icon",
                modifier = Modifier
                    .width(maxWidth * 0.25f).height(maxHeight * 0.05f).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        component.navigateBack()
                    }
            )
            Spacer(modifier = Modifier.height(maxHeight * 0.02f))

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

                InfoLoggingFormStatus(1, maxHeight, maxWidth)

                Spacer(modifier = Modifier.height(20.dp))

                when (sharedPreferences.getUserType()) {

                    "Production" -> {
                        ProductionScreen(component, responseProduction, true, Data(),maxHeight)
                    }

                    "Accounts" -> {
                        AccountScreen(component, AccountDepartment(), true, Data(),maxHeight)
                    }

                    "Dispatch" -> {
                        DispatchScreen(component, DispatchDepartment(), true, Data(),maxHeight)
                    }

                    "Support" -> {
                        ServiceEngineerScreen(component, ServiceDepartment(), true, Data(),maxHeight)
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
                        val response =
                            (singleDeviceResponse as StateClass.UiState.Success<SingleDeviceResponse>).data.data

                        var statusNumber = 0
                        if (response.productionDepartment.isNotEmpty()) {
                            statusNumber++
                        }
                        if (response.accountDepartment.isNotEmpty()) {
                            statusNumber++
                        }
                        if (response.dispatchDepartment.isNotEmpty()) {
                            statusNumber++
                        }
                        if (response.serviceDepartment.isNotEmpty()) {
                            statusNumber++
                        }

                        SharedLogger.i("StatusNumber : $statusNumber | Response : $response")
                        InfoLoggingFormStatus(statusNumber, maxHeight, maxWidth)

                        Spacer(modifier = Modifier.height(20.dp))

                        when (sharedPreferences.getUserType()) {

                            "Production" -> {
                                ProductionScreen(
                                    component,
                                    response.productionDepartment[0],
                                    false,
                                    response,
                                    maxHeight
                                )
                            }

                            "Accounts" -> {
                                AccountScreen(
                                    component,
                                    if (response.accountDepartment.isEmpty()) AccountDepartment() else response.accountDepartment[0],
                                    false,
                                    response,
                                    maxHeight
                                )
                            }

                            "Dispatch" -> {
                                DispatchScreen(
                                    component,
                                    if (response.dispatchDepartment.isEmpty()) DispatchDepartment() else response.dispatchDepartment[0],
                                    false,
                                    response,
                                    maxHeight
                                )
                            }

                            "Support" -> {
                                ServiceEngineerScreen(
                                    component,
                                    if (response.serviceDepartment.isEmpty()) ServiceDepartment() else response.serviceDepartment[0],
                                    false,
                                    response,
                                    maxHeight
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