package org.example.internal_logcat.presentation.ui.mini_features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.production_text
import internallogcat.composeapp.generated.resources.rem_bold
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_regular
import internallogcat.composeapp.generated.resources.rem_semibold
import internallogcat.composeapp.generated.resources.submit_text
import kotlinx.coroutines.launch
import org.example.internal_logcat.presentation.ui.composables.DropDownCustom
import org.example.internal_logcat.domain.models.request.AccountDepartment
import org.example.internal_logcat.domain.models.request.DispatchDepartment
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.request.ServiceDepartment
import org.example.internal_logcat.domain.models.response.Data
import org.example.internal_logcat.domain.models.response.File
import org.example.internal_logcat.domain.models.response.ProductionDepartment
import org.example.internal_logcat.domain.models.response.UploadFileResponse
import org.example.internal_logcat.getPlatform
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.example.internal_logcat.presentation.ui.composables.errorText
import org.example.internal_logcat.utils.AppColors
import org.example.internal_logcat.utils.SharedLogger
import org.example.internal_logcat.utils.StateClass

@Composable
fun ProductionScreen(
    component: FormComponent,
    response: ProductionDepartment,
    isNewDevice: Boolean,
    completeResponse: Data
) {

    val type = listOf("Demo", "Sale", "Replacement")
    val model = listOf("Agvac Pro Universal", "Agvac Pro ATP", "Agvac Pro ATN")
    val screenSize = listOf("15 Inch", "24 Inch")
    val exhaleValveType = listOf("Reusable", "Disposable")
    val neoSensorType = listOf("Reusable", "Disposable")

    val state by component.uploadFileResponse.collectAsState()
    val context = LocalPlatformContext.current // ✅ Calf helper

    val scope = rememberCoroutineScope()
    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Pdf,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.forEach {
                    component.uploadFileOnServer(true,it,context)
                }
            }
        }
    )

    var serialNumber by remember { mutableStateOf(response.serialNumber) }
    var serialNumberError by remember { mutableStateOf<String?>(null) }
    var piMacAddress by remember { mutableStateOf(response.ipMacAddress) }
    var piMacAddressError by remember { mutableStateOf<String?>(null) }
    var androidMacAddress by remember { mutableStateOf(response.androidMacAddress) }
    var androidMacAddressError by remember { mutableStateOf<String?>(null) }
    var logDeviceId by remember { mutableStateOf(response.deviceId) }
    var logDeviceIdError by remember { mutableStateOf<String?>(null) }
    var softwareVersion by remember { mutableStateOf(response.softwareVersion) }
    var softwareVersionError by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf(response.description) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf(response.type) }
    var selectedModel by remember { mutableStateOf(response.model) }
    var selectedScreenSize by remember { mutableStateOf(response.screenSize) }
    var selectedExhaleValveType by remember { mutableStateOf(response.exhaleValveType) }
    var selectedNeoSensorType by remember { mutableStateOf(response.neoSensorType) }
    var filePath by remember { mutableStateOf(response.dhrFile) }

    var fileName by remember { mutableStateOf(
        if (filePath.isNotEmpty()) filePath.split(".com")[1].split("/")[1] else ""
    )}

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(Res.string.production_text),
            color = AppColors.textGrey,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Serial Number",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            serialNumber,
            onValueChange = {
                serialNumber = it
                serialNumberError = null
            },
            "Enter Serial Number"
        )

        serialNumberError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pi MAC Address",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            piMacAddress,
            onValueChange = {
                piMacAddress = it
                piMacAddressError = null
            },
            "Enter Pi Mac Address"
        )
        piMacAddressError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Android MAC Address",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            androidMacAddress,
            onValueChange = {
                androidMacAddress = it
                androidMacAddressError = null
            },
            "Enter Android Mac Address"
        )
        androidMacAddressError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Log Device ID",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            logDeviceId,
            onValueChange = {
                logDeviceId = it
                logDeviceIdError = null
            },
            "Enter Log Device ID"
        )
        logDeviceIdError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Type",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        selectedType = DropDownCustom(type)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Model",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        selectedModel = DropDownCustom(model)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Screen Size",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        selectedScreenSize = DropDownCustom(screenSize)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Software Version",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            softwareVersion,
            onValueChange = {
                softwareVersion = it
                softwareVersionError = null
            },
            "Enter Software Version"
        )
        softwareVersionError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Exhale Valve Type",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        selectedExhaleValveType = DropDownCustom(exhaleValveType)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Neo Sensor Type",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        selectedNeoSensorType = DropDownCustom(neoSensorType)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Description",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            description,
            onValueChange = {
                description = it
                descriptionError = null
            },
            "Enter Description"
        )
        descriptionError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                pickerLauncher.launch()
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.buttonDarkGrey,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Upload DHR",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when(state){

            is StateClass.UiState.Error -> {
                fileName = "File Not Found"
            }
            is StateClass.UiState.Success -> {
                filePath = (state as StateClass.UiState.Success<UploadFileResponse>).data.file?.path ?: ""
                fileName = (state as StateClass.UiState.Success<UploadFileResponse>).data.file?.originalName ?: ""
            }
            is StateClass.UiState.Idle -> {}
            is StateClass.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        if (fileName.isNotEmpty()) {
            Text(
                text = fileName,
                color = AppColors.errorColor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.rem_regular)),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (serialNumber.isEmpty()) {
                    serialNumberError = "Please Enter Serial Number"
                } else if (piMacAddress.isEmpty()) {
                    piMacAddressError = "Please Enter Pi Mac Address"
                } else if (androidMacAddress.isEmpty()) {
                    androidMacAddressError = "Please Enter Android Mac Address"
                } else if (logDeviceId.isEmpty()) {
                    logDeviceIdError = "Please Enter Log Device ID"
                } else if (softwareVersion.isEmpty()) {
                    softwareVersionError = "Please Enter Software Version"
                } else if (description.isEmpty()) {
                    descriptionError = "Please Enter Description"
                }else if (filePath.isEmpty()){
                    fileName = "Please Select File"
                } else {
                    serialNumberError = null
                    piMacAddressError = null
                    androidMacAddressError = null
                    logDeviceIdError = null
                    softwareVersionError = null
                    descriptionError = null

                    if (isNewDevice) {

                        val formRequest = FormRequest(
                            productionDepartment = arrayListOf(
                                org.example.internal_logcat.domain.models.request.ProductionDepartment(
                                    serialNumber = serialNumber,
                                    ipMacAddress = piMacAddress,
                                    androidMacAddress = androidMacAddress,
                                    deviceId = logDeviceId,
                                    softwareVersion = softwareVersion,
                                    description = description,
                                    type = selectedType,
                                    model = selectedModel,
                                    screenSize = selectedScreenSize,
                                    exhaleValveType = selectedExhaleValveType,
                                    neoSensorType = selectedNeoSensorType,
                                    dhrFile = filePath
                                )
                            ),
                        )
                        // need to call api
                        component.addDeviceUsingForm(formRequest)
                    } else {

                        val formRequest = FormRequest(
                            productionDepartment = arrayListOf(
                                org.example.internal_logcat.domain.models.request.ProductionDepartment(
                                    serialNumber = serialNumber,
                                    ipMacAddress = piMacAddress,
                                    androidMacAddress = androidMacAddress,
                                    deviceId = logDeviceId,
                                    softwareVersion = softwareVersion,
                                    description = description,
                                    type = selectedType,
                                    model = selectedModel,
                                    screenSize = selectedScreenSize,
                                    exhaleValveType = selectedExhaleValveType,
                                    neoSensorType = selectedNeoSensorType,
                                    dhrFile = filePath
                                )
                            ),

                            // here we check if response is not empty then response else empty array
                            accountDepartment = if (completeResponse.accountDepartment.isEmpty()) ArrayList() else arrayListOf(
                                AccountDepartment(
                                    amount = completeResponse.accountDepartment[0].amount,
                                    invoiceNumber = completeResponse.accountDepartment[0].invoiceNumber,
                                    shippedTo = completeResponse.accountDepartment[0].shippedTo,
                                    billedTo = completeResponse.accountDepartment[0].billedTo,
                                    deliveryNote = completeResponse.accountDepartment[0].deliveryNote,
                                    deliveryBill = completeResponse.accountDepartment[0].deliveryBill,
                                    ewayBill = completeResponse.accountDepartment[0].ewayBill
                                )
                            ),
                            dispatchDepartment = if (completeResponse.dispatchDepartment.isEmpty()) ArrayList() else arrayListOf(
                                DispatchDepartment(
                                    address = completeResponse.dispatchDepartment[0].address,
                                    city = completeResponse.dispatchDepartment[0].city,
                                    hospitalName = completeResponse.dispatchDepartment[0].hospitalName,
                                    state = completeResponse.dispatchDepartment[0].state
                                )
                            ),
                            serviceDepartment = if (completeResponse.serviceDepartment.isEmpty()) ArrayList() else arrayListOf(
                                ServiceDepartment(
                                    installationDate = completeResponse.serviceDepartment[0].installationDate,
                                    dispatchDate = completeResponse.serviceDepartment[0].dispatchDate,
                                    serviceEngineerName = completeResponse.serviceDepartment[0].serviceEngineerName,
                                    installationOrFeedbackReport = completeResponse.serviceDepartment[0].installationOrFeedbackReport
                                )
                            )
                        )
                        // need to call api
                        component.updateDeviceUsingForm(completeResponse.id, formRequest)
                    }
                }
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.themeGreenColor,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(Res.string.submit_text),
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}