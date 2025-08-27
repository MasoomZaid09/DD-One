package org.dd_healthcare.internal_logcat.presentation.ui.mini_features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.calf.core.LocalPlatformContext
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
import org.dd_healthcare.internal_logcat.presentation.ui.composables.DropDownCustom
import org.dd_healthcare.internal_logcat.domain.models.request.AccountDepartment
import org.dd_healthcare.internal_logcat.domain.models.request.DispatchDepartment
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.dd_healthcare.internal_logcat.domain.models.request.FormRequest
import org.dd_healthcare.internal_logcat.domain.models.request.ServiceDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.Data
import org.dd_healthcare.internal_logcat.domain.models.response.ProductionDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.UploadFileResponse
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.dd_healthcare.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.dd_healthcare.internal_logcat.presentation.ui.composables.errorText
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.StateClass
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun ProductionScreen(
    component: FormComponent,
    response: ProductionDepartment,
    isNewDevice: Boolean,
    completeResponse: Data,
    maxHeight : Dp
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
                    component.uploadFileOnServer(true, it, context)
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

    var fileName by remember {
        mutableStateOf(
            if (filePath.isNotEmpty()) filePath.split(".com")[1].split("/")[1] else ""
        )
    }


    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        Text(
            text = stringResource(Res.string.production_text),
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.02f),
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.03f))

        Text(
            text = "Serial Number",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            serialNumber,
            onValueChange = {
                serialNumber = it
                serialNumberError = null
            },
            "Enter Serial Number",
            (maxHeight * 0.017f)
        )

        serialNumberError?.let { errorText(it, (maxHeight * 0.016f)) }
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Pi MAC Address",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            piMacAddress,
            onValueChange = {
                piMacAddress = it
                piMacAddressError = null
            },
            "Enter Pi Mac Address",
            (maxHeight * 0.017f)
        )
        piMacAddressError?.let { errorText(it, (maxHeight * 0.016f)) }
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Android MAC Address",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            androidMacAddress,
            onValueChange = {
                androidMacAddress = it
                androidMacAddressError = null
            },
            "Enter Android Mac Address",
            (maxHeight * 0.017f)
        )
        androidMacAddressError?.let { errorText(it, (maxHeight * 0.016f)) }
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Log Device ID",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            logDeviceId,
            onValueChange = {
                logDeviceId = it
                logDeviceIdError = null
            },
            "Enter Log Device ID",
            (maxHeight * 0.017f)
        )
        logDeviceIdError?.let { errorText(it, (maxHeight * 0.016f)) }
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Type",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        selectedType = DropDownCustom(type)
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Model",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        selectedModel = DropDownCustom(model)
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Screen Size",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))
        selectedScreenSize = DropDownCustom(screenSize)
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Software Version",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            softwareVersion,
            onValueChange = {
                softwareVersion = it
                softwareVersionError = null
            },
            "Enter Software Version",
            (maxHeight * 0.017f)
        )
        softwareVersionError?.let { errorText(it, (maxHeight * 0.016f)) }
        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Exhale Valve Type",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        selectedExhaleValveType = DropDownCustom(exhaleValveType)

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Neo Sensor Type",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        selectedNeoSensorType = DropDownCustom(neoSensorType)

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Description",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(
            description,
            onValueChange = {
                description = it
                descriptionError = null
            },
            "Enter Description",
            (maxHeight * 0.017f)
        )
        descriptionError?.let { errorText(it, (maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

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
                .fillMaxWidth().height(maxHeight * 0.065f)
        ) {
            Text(
                text = "Upload DHR",
                fontSize = fixedSp(maxHeight * 0.018f),
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.01f))

        when (state) {

            is StateClass.UiState.Error -> {
                fileName = "File Not Found"
            }

            is StateClass.UiState.Success -> {
                filePath =
                    (state as StateClass.UiState.Success<UploadFileResponse>).data.file?.path
                        ?: ""
                fileName =
                    (state as StateClass.UiState.Success<UploadFileResponse>).data.file?.originalName
                        ?: ""
            }

            is StateClass.UiState.Idle -> {}
            is StateClass.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = AppColors.themeGreenColor)
            }
        }

        if (fileName.isNotEmpty()) {
            Text(
                text = fileName,
                color = AppColors.errorColor,
                fontSize = fixedSp(maxHeight * 0.014f),
                fontFamily = FontFamily(Font(Res.font.rem_regular)),
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

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
                } else if (filePath.isEmpty()) {
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
                                org.dd_healthcare.internal_logcat.domain.models.request.ProductionDepartment(
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
                                org.dd_healthcare.internal_logcat.domain.models.request.ProductionDepartment(
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
                .height(maxHeight * 0.065f)
        ) {
            Text(
                text = stringResource(Res.string.submit_text),
                fontSize = fixedSp(maxHeight * 0.018f),
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.01f))
    }
}
