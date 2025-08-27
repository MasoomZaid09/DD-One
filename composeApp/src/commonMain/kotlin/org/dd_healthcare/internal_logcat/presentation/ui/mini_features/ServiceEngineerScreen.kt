package org.dd_healthcare.internal_logcat.presentation.ui.mini_features


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.rem_bold
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_regular
import internallogcat.composeapp.generated.resources.rem_semibold
import internallogcat.composeapp.generated.resources.service_text
import internallogcat.composeapp.generated.resources.submit_text
import kotlinx.coroutines.launch
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import org.dd_healthcare.internal_logcat.domain.models.request.AccountDepartment
import org.dd_healthcare.internal_logcat.domain.models.request.DispatchDepartment
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.dd_healthcare.internal_logcat.domain.models.request.FormRequest
import org.dd_healthcare.internal_logcat.domain.models.request.ProductionDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.Data
import org.dd_healthcare.internal_logcat.domain.models.response.ServiceDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.UploadFileResponse
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.dd_healthcare.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.dd_healthcare.internal_logcat.presentation.ui.composables.errorText
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.StateClass
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun ServiceEngineerScreen(
    component: FormComponent,
    response: ServiceDepartment,
    isNewDevice: Boolean,
    completeResponse: Data,
    maxHeight: Dp
) {
    var serviceEngineerName by remember { mutableStateOf(response.serviceEngineerName) }
    var serviceEngineerNameError by remember { mutableStateOf<String?>(null) }
    var installationDate by remember { mutableStateOf(response.installationDate.ifEmpty { "Enter Installation Date" }) }
    var installationDateError by remember { mutableStateOf<String?>(null) }
    var dispatchDate by remember { mutableStateOf(response.dispatchDate.ifEmpty { "Enter Dispatch Date" }) }
    var dispatchDateError by remember { mutableStateOf<String?>(null) }
    var filePath by remember { mutableStateOf(response.installationOrFeedbackReport) }

    var fileName by remember { mutableStateOf(
        if (filePath.isNotEmpty()) filePath.split(".com")[1].split("/")[1] else ""
    )}

    val state by component.uploadFileResponse.collectAsState()
    val context = LocalPlatformContext.current

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

    var stateOfInstallDatePicker by remember { mutableStateOf(false) }
    var stateOfDispatchDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(Res.string.service_text),
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.02f),
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.03f))

        Text(
            text = "Service Engineer Name",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(serviceEngineerName, onValueChange = {
            serviceEngineerName = it
            serviceEngineerNameError = null
        }, "Enter Service Engineer Name",
            maxHeight * 0.016f)

        serviceEngineerNameError?.let { errorText(it, (maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Installation Date",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.height(50.dp).fillMaxWidth().clip(RoundedCornerShape(20))
                .background(color = AppColors.editTextColor).clickable {
                    stateOfInstallDatePicker = true
                }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = installationDate,
                fontSize = fixedSp(maxHeight * 0.017f),
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )
        }

        installationDateError?.let { errorText(it,maxHeight * 0.016f) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Dispatch Date",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.height(50.dp).fillMaxWidth().clip(RoundedCornerShape(20))
                .background(color = AppColors.editTextColor).clickable {
                    stateOfDispatchDatePicker = true
                }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = dispatchDate,
                fontSize = fixedSp(maxHeight * 0.017f),
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )
        }
        dispatchDateError?.let { errorText(it,maxHeight * 0.016f) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Button(
            onClick = {
                pickerLauncher.launch()
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.buttonDarkGrey,
                contentColor = AppColors.whiteColor
            ), modifier = Modifier.fillMaxWidth().height(maxHeight * 0.065f)
        ) {
            Text(
                text = "UPLOAD REPORT",
                fontSize = fixedSp(maxHeight * 0.018f),
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.01f))

        // getting state of file upload
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
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally),color = AppColors.themeGreenColor)
            }
        }

        if (fileName.isNotEmpty()) {
            Text(
                text = fileName,
                color = AppColors.errorColor,
                fontSize = fixedSp(maxHeight * 0.016f),
                fontFamily = FontFamily(Font(Res.font.rem_regular)),
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Button(
            onClick = {
                if (serviceEngineerName.isEmpty()) {
                    serviceEngineerNameError = "Please Enter Name"
                }else if (installationDate == "Enter Installation Date") {
                    installationDateError = "Please select installation date"
                }else if (dispatchDate == "Enter Dispatch Date") {
                    dispatchDateError = "Please select dispatch date"
                } else if (filePath.isEmpty()){
                    fileName = "Please select file"
                } else  {
                    serviceEngineerNameError = null
                    installationDateError = null
                    dispatchDateError = null

                    if (isNewDevice) {

                        val formRequest = FormRequest(
                            serviceDepartment = arrayListOf(
                                org.dd_healthcare.internal_logcat.domain.models.request.ServiceDepartment(
                                    serviceEngineerName = serviceEngineerName,
                                    installationDate = installationDate,
                                    dispatchDate = dispatchDate,
                                    installationOrFeedbackReport = filePath
                                )
                            ),
                            )
                        // need to call api
                        component.addDeviceUsingForm(formRequest)
                    } else {
                        val formRequest = FormRequest(
                            productionDepartment = if (completeResponse.productionDepartment.isEmpty()) ArrayList() else arrayListOf(
                                ProductionDepartment(
                                    serialNumber = completeResponse.productionDepartment[0].serialNumber,
                                    ipMacAddress = completeResponse.productionDepartment[0].ipMacAddress,
                                    androidMacAddress = completeResponse.productionDepartment[0].androidMacAddress,
                                    deviceId = completeResponse.productionDepartment[0].deviceId,
                                    softwareVersion = completeResponse.productionDepartment[0].softwareVersion,
                                    description = completeResponse.productionDepartment[0].description,
                                    type = completeResponse.productionDepartment[0].type,
                                    model = completeResponse.productionDepartment[0].model,
                                    screenSize = completeResponse.productionDepartment[0].screenSize,
                                    exhaleValveType = completeResponse.productionDepartment[0].exhaleValveType,
                                    neoSensorType = completeResponse.productionDepartment[0].neoSensorType
                                )
                            ),
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
                            serviceDepartment = arrayListOf(
                                org.dd_healthcare.internal_logcat.domain.models.request.ServiceDepartment(
                                    installationDate = installationDate,
                                    dispatchDate = dispatchDate,
                                    serviceEngineerName = serviceEngineerName,
                                    installationOrFeedbackReport = filePath
                                )
                            )

                        )
                        // need to call api
                        component.updateDeviceUsingForm(completeResponse.id, formRequest)
                    }
                }
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.themeGreenColor,
                contentColor = AppColors.whiteColor
            ), modifier = Modifier.fillMaxWidth().height(maxHeight * 0.065f)
        ) {
            Text(
                text = stringResource(Res.string.submit_text),
                fontSize = fixedSp(maxHeight * 0.018f),
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(maxHeight * 0.01f))

        WheelDatePickerView(
            showDatePicker = stateOfInstallDatePicker,
            height = maxHeight * 0.40f,
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            rowCount = 3,
            titleStyle = TextStyle(
                fontSize = fixedSp(maxHeight * 0.017f), color = AppColors.blackColor
            ),
            doneLabelStyle = TextStyle(
                fontSize = fixedSp(maxHeight * 0.017f), color = AppColors.blackColor
            ),
            onDoneClick = {
                stateOfInstallDatePicker = false
                installationDate = it.toString()
                installationDateError = null
            },
            onDismiss = {
                stateOfInstallDatePicker = false
            },

            )

        WheelDatePickerView(
            showDatePicker = stateOfDispatchDatePicker,
            height = maxHeight * 0.40f,
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            rowCount = 3,
            titleStyle = TextStyle(
                fontSize = fixedSp(maxHeight * 0.017f), color = AppColors.blackColor
            ),
            doneLabelStyle = TextStyle(
                fontSize = fixedSp(maxHeight * 0.017f), color = AppColors.blackColor
            ),
            onDoneClick = {
                stateOfDispatchDatePicker = false
                dispatchDate = it.toString()
                dispatchDateError = null
            },
            onDismiss = {
                stateOfDispatchDatePicker = false
            },

            )

    }
}