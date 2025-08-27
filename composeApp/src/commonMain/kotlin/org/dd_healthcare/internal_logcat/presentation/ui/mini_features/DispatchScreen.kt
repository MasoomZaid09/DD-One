package org.dd_healthcare.internal_logcat.presentation.ui.mini_features


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.dispatch_text
import internallogcat.composeapp.generated.resources.rem_bold
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_semibold
import internallogcat.composeapp.generated.resources.submit_text
import org.dd_healthcare.internal_logcat.domain.models.request.AccountDepartment
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.dd_healthcare.internal_logcat.domain.models.request.FormRequest
import org.dd_healthcare.internal_logcat.domain.models.request.ProductionDepartment
import org.dd_healthcare.internal_logcat.domain.models.request.ServiceDepartment
import org.dd_healthcare.internal_logcat.domain.models.response.Data
import org.dd_healthcare.internal_logcat.domain.models.response.DispatchDepartment
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.dd_healthcare.internal_logcat.presentation.ui.composables.RoundedEditTextNormal
import org.dd_healthcare.internal_logcat.presentation.ui.composables.errorText
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun DispatchScreen(
    component: FormComponent,
    response: DispatchDepartment,
    isNewDevice: Boolean,
    completeResponse: Data,
    maxHeight: Dp
) {

    var hospitalName by remember { mutableStateOf(response.hospitalName) }
    var hospitalNameError by remember { mutableStateOf<String?>(null) }
    var address by remember { mutableStateOf(response.address) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var city by remember { mutableStateOf(response.city) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var state by remember { mutableStateOf(response.state) }
    var stateError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(Res.string.dispatch_text),
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.02f),
            fontFamily = FontFamily(Font(Res.font.rem_semibold)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.03f))

        Text(
            text = "Hospital Name",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(hospitalName, onValueChange = {
            hospitalName = it
            hospitalNameError = null
        }, "Enter Hospital Name",
            maxHeight * 0.017f)

        hospitalNameError?.let { errorText(it,(maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "Address",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(address, onValueChange = {
            address = it
            addressError = null
        }, "Enter Address",
            maxHeight * 0.017f)

        addressError?.let { errorText(it,(maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "City",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )

        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(city, onValueChange = {
            city = it
            cityError = null
        }, "Enter City",
            maxHeight * 0.017f)
        cityError?.let { errorText(it,(maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Text(
            text = "State",
            color = AppColors.textGrey,
            fontSize = fixedSp(maxHeight * 0.017f),
            fontFamily = FontFamily(Font(Res.font.rem_medium)),
        )
        Spacer(modifier = Modifier.height(maxHeight * 0.02f))

        RoundedEditTextNormal(state, onValueChange = {
            state = it
            stateError = null
        }, "State",
            maxHeight * 0.017f)

        stateError?.let { errorText(it,(maxHeight * 0.016f)) }

        Spacer(modifier = Modifier.height(maxHeight * 0.025f))

        Button(
            onClick = {
                if (hospitalName.isEmpty()) {
                    hospitalNameError = "Please Enter Hospital Name"
                } else if (address.isEmpty()) {
                    addressError = "Please Enter Address"
                } else if (city.isEmpty()) {
                    cityError = "Please Enter City"
                } else if (state.isEmpty()) {
                    stateError = "Please Enter State"
                } else {
                    hospitalNameError = null
                    addressError = null
                    cityError = null
                    stateError = null

                    if (isNewDevice) {
                        val formRequest = FormRequest(
                            dispatchDepartment = arrayListOf(
                                org.dd_healthcare.internal_logcat.domain.models.request.DispatchDepartment(
                                    hospitalName = hospitalName,
                                    state = state,
                                    city = city,
                                    address = address
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
                            dispatchDepartment = arrayListOf(
                                org.dd_healthcare.internal_logcat.domain.models.request.DispatchDepartment(
                                    address = address,
                                    city = city,
                                    hospitalName = hospitalName,
                                    state = state
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
                contentColor = AppColors.whiteColor
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