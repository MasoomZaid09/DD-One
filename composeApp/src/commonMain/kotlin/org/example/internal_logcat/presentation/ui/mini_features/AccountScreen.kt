package org.example.internal_logcat.presentation.ui.mini_features


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.account_text
import internallogcat.composeapp.generated.resources.rem_bold
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_regular
import internallogcat.composeapp.generated.resources.rem_semibold
import internallogcat.composeapp.generated.resources.submit_text
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.example.internal_logcat.domain.models.request.FormRequest
import org.example.internal_logcat.domain.models.request.AccountDepartment
import org.example.internal_logcat.domain.models.request.DispatchDepartment
import org.example.internal_logcat.domain.models.request.ProductionDepartment
import org.example.internal_logcat.domain.models.request.ServiceDepartment
import org.example.internal_logcat.domain.models.response.Data
import org.example.internal_logcat.domain.models.response.UploadFileResponse
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.example.internal_logcat.presentation.ui.composables.errorText
import org.example.internal_logcat.utils.AppColors
import org.example.internal_logcat.utils.SharedLogger
import org.example.internal_logcat.utils.StateClass

@Composable
fun AccountScreen(
    component: FormComponent,
    response: org.example.internal_logcat.domain.models.response.AccountDepartment,
    isNewDevice: Boolean,
    completeResponse : Data
) {

    var billedTo by remember { mutableStateOf(response.billedTo) }
    var billedToError by remember { mutableStateOf<String?>(null) }
    var shippedTo by remember { mutableStateOf(response.shippedTo) }
    var shippedToError by remember { mutableStateOf<String?>(null) }
    var invoiceNumber by remember { mutableStateOf(response.invoiceNumber) }
    var invoiceNumberError by remember { mutableStateOf<String?>(null) }
    var amount by remember { mutableStateOf(response.amount) }
    var amountError by remember { mutableStateOf<String?>(null) }
    var deliveryNote by remember { mutableStateOf(response.deliveryNote) }
    var deliveryNoteError by remember { mutableStateOf<String?>(null) }
    var deliveryBillPath by remember { mutableStateOf(response.deliveryBill) }
    var ewayBillPath by remember { mutableStateOf(response.ewayBill) }

    SharedLogger.i("Path : $deliveryBillPath , $ewayBillPath")

    var deliveryBillName by remember { mutableStateOf(if (deliveryBillPath.isNotEmpty()) deliveryBillPath.split(".com")[1].split("/")[1] else "")}
    var ewayBillName by remember { mutableStateOf(
        if (ewayBillPath.isNotEmpty()) ewayBillPath.split(".com")[1].split("/")[1] else "")}
    val context = LocalPlatformContext.current

    val stateFirst by component.uploadFileResponse.collectAsState()
    val stateSecond by component.uploadFileResponseSecond.collectAsState()

    val scopeDeliveryNote = rememberCoroutineScope()
    val picDeliveryNote = rememberFilePickerLauncher(
        type = FilePickerFileType.Pdf,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scopeDeliveryNote.launch {
                files.forEach {
                    component.uploadFileOnServer(true,it,context)
                }
            }
        }
    )

    val scopeEwayBill = rememberCoroutineScope()
    val picEwayBill = rememberFilePickerLauncher(
        type = FilePickerFileType.Pdf,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scopeEwayBill.launch {
                files.forEach {
                    component.uploadFileOnServer(false,it,context)
                }
            }
        }
    )

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(Res.string.account_text),
            color = AppColors.textGrey,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Billed To",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(billedTo, onValueChange = {
            billedTo = it
            billedToError = null
        }, "Enter Billed To")
        billedToError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Shipped to",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(shippedTo, onValueChange = {
            shippedTo = it
            shippedToError = null
        }, "Enter Shipped To")
        shippedToError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Invoice Number",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            invoiceNumber, onValueChange = {
                invoiceNumber = it
                invoiceNumberError = null
            }, "Enter Invoice Number"
        )
        invoiceNumberError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Amount",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(amount, onValueChange = {
            amount = it
            amountError = null
        }, "Enter Amount")
        amountError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Delivery Note",
            color = AppColors.textGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(7.dp))

        RoundedEditTextNormal(
            deliveryNote, onValueChange = {
                deliveryNote = it
                deliveryNoteError = null
            }, "Enter Delivery Note"
        )
        deliveryNoteError?.let { errorText(it) }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                picDeliveryNote.launch()
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.buttonDarkGrey
            ), modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(
                text = "UPLOAD DELIVERY/RECEIVE NOTE",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when(stateFirst){

            is StateClass.UiState.Error -> {
                deliveryBillName = "File Not Found"
            }
            is StateClass.UiState.Success -> {
                deliveryBillPath = (stateFirst as StateClass.UiState.Success<UploadFileResponse>).data.file?.path ?: ""
                deliveryBillName = (stateFirst as StateClass.UiState.Success<UploadFileResponse>).data.file?.originalName ?: ""
            }
            is StateClass.UiState.Idle -> {}
            is StateClass.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        if (deliveryBillName.isNotEmpty()) {
            Text(
                text = deliveryBillName,
                color = AppColors.errorColor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.rem_regular)),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                picEwayBill.launch()
            }, shape = RoundedCornerShape(20), colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.buttonDarkGrey
            ), modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(
                text = "UPLOAD EWAY BILL",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.rem_bold))
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when(stateSecond){

            is StateClass.UiState.Error -> {
                ewayBillName = "File Not Found"
            }
            is StateClass.UiState.Success -> {
                ewayBillPath = (stateSecond as StateClass.UiState.Success<UploadFileResponse>).data.file?.path ?: ""
                ewayBillName = (stateSecond as StateClass.UiState.Success<UploadFileResponse>).data.file?.originalName ?: ""
            }
            is StateClass.UiState.Idle -> {}
            is StateClass.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        if (ewayBillName.isNotEmpty()) {
            Text(
                text = ewayBillName,
                color = AppColors.errorColor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.rem_regular)),
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (billedTo.isEmpty()) {
                    billedToError = "Please Enter Billed To"
                } else if (shippedTo.isEmpty()) {
                    shippedToError = "Please Enter Shipped To"
                } else if (invoiceNumber.isEmpty()) {
                    invoiceNumberError = "Please Enter invoice Number"
                } else if (amount.isEmpty()) {
                    amountError = "Please Enter Amount"
                } else if (deliveryNote.isEmpty()) {
                    deliveryNoteError = "Please Enter Delivery Note"
                }else if (deliveryBillPath.isEmpty()){
                    deliveryBillName = "Please select file"
                }else if (ewayBillPath.isEmpty()){
                    ewayBillName = "Please select file"
                } else {
                    billedToError = null
                    shippedToError = null
                    invoiceNumberError = null
                    amountError = null
                    deliveryNoteError = null

                    if (isNewDevice) {
                        val formRequest = FormRequest(
                            accountDepartment = arrayListOf(
                                AccountDepartment(
                                    amount = amount,
                                    billedTo = billedTo,
                                    shippedTo = shippedTo,
                                    deliveryNote = deliveryNote,
                                    invoiceNumber = invoiceNumber,
                                    deliveryBill = deliveryBillPath,
                                    ewayBill = ewayBillPath
                                )
                            )
                        )

                        component.addDeviceUsingForm(formRequest)
                    }else {
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
                            accountDepartment = arrayListOf(
                                AccountDepartment(
                                    amount = amount,
                                    invoiceNumber = invoiceNumber,
                                    shippedTo = shippedTo,
                                    billedTo = billedTo,
                                    deliveryNote = deliveryNote,
                                    deliveryBill = deliveryBillPath,
                                    ewayBill = ewayBillPath
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
    }
}