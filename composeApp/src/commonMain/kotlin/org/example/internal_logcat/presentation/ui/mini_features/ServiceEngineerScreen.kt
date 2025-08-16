package org.example.internal_logcat.presentation.ui.mini_features


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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
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
import org.example.internal_logcat.domain.models.request.AccountDepartment
import org.example.internal_logcat.domain.models.request.DispatchDepartment
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.request.ProductionDepartment
import org.example.internal_logcat.domain.models.response.Data
import org.example.internal_logcat.domain.models.response.ServiceDepartment
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.example.internal_logcat.presentation.ui.composables.errorText
import org.example.internal_logcat.utils.AppColors
import org.example.internal_logcat.utils.SharedLogger

@Composable
fun ServiceEngineerScreen(
    component: FormComponent,
    response: ServiceDepartment,
    isNewDevice: Boolean,
    completeResponse: Data
) {
    var serviceEngineerName by remember { mutableStateOf(response.serviceEngineerName) }
    var serviceEngineerNameError by remember { mutableStateOf<String?>(null) }
    var installationDate by remember { mutableStateOf(response.installationDate.ifEmpty { "Enter Installation Date" }) }
    var dispatchDate by remember { mutableStateOf(response.dispatchDate.ifEmpty { "Enter Dispatch Date" }) }

    // file picker
    val scope = rememberCoroutineScope()
    var pdfFileName by remember { mutableStateOf<String?>(null) }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Pdf,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                SharedLogger.i(files.size.toString())
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
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Service Engineer Name",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(serviceEngineerName, onValueChange = {
            serviceEngineerName = it
            serviceEngineerNameError = null
        }, "Enter Service Engineer Name")
        serviceEngineerNameError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Installation Date",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

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
                fontSize = 13.sp,
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Dispatch Date",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

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
                fontSize = 13.sp,
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                pickerLauncher.launch()
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.buttonDarkGrey
            ), modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(
                text = "UPLOAD REPORT",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (serviceEngineerName.isEmpty()) {
                    serviceEngineerNameError = "Please Enter Name"
                } else {
                    serviceEngineerNameError = null

                    if (isNewDevice) {

                        val formRequest = FormRequest(
                            serviceDepartment = arrayListOf(
                                org.example.internal_logcat.domain.models.request.ServiceDepartment(
                                    serviceEngineerName = serviceEngineerName,
                                    installationDate = installationDate,
                                    dispatchDate = dispatchDate,
                                    installationOrFeedbackReport = ""
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
                                org.example.internal_logcat.domain.models.request.ServiceDepartment(
                                    installationDate = installationDate,
                                    dispatchDate = dispatchDate,
                                    serviceEngineerName = serviceEngineerName,
                                    installationOrFeedbackReport = ""
                                )
                            )

                        )
                        // need to call api
                        component.updateDeviceUsingForm(completeResponse.id, formRequest)
                    }
                }
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.themeGreenColor
            ), modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(
                text = stringResource(Res.string.submit_text),
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        WheelDatePickerView(
            showDatePicker = stateOfInstallDatePicker,
            height = 80.dp,
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            rowCount = 3,
            titleStyle = TextStyle(
                fontSize = 12.sp, color = AppColors.blackColor
            ),
            doneLabelStyle = TextStyle(
                fontSize = 12.sp, color = AppColors.blackColor
            ),
            onDoneClick = {
                stateOfInstallDatePicker = false
                installationDate = it.toString()
            },
            onDismiss = {
                stateOfInstallDatePicker = false
            },

            )

        WheelDatePickerView(
            showDatePicker = stateOfDispatchDatePicker,
            height = 80.dp,
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            rowCount = 3,
            titleStyle = TextStyle(
                fontSize = 12.sp, color = AppColors.blackColor
            ),
            doneLabelStyle = TextStyle(
                fontSize = 12.sp, color = AppColors.blackColor
            ),
            onDoneClick = {
                stateOfDispatchDatePicker = false
                dispatchDate = it.toString()
            },
            onDismiss = {
                stateOfDispatchDatePicker = false
            },

            )

    }
}