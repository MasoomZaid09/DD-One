package org.dd_healthcare.internal_logcat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.description_logo
import internallogcat.composeapp.generated.resources.edit_logo
import internallogcat.composeapp.generated.resources.header_text
import internallogcat.composeapp.generated.resources.in_logo
import internallogcat.composeapp.generated.resources.log_out_logo
import internallogcat.composeapp.generated.resources.no_data_text
import internallogcat.composeapp.generated.resources.out_logo
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_regular
import internallogcat.composeapp.generated.resources.rem_semibold
import kotlinx.coroutines.launch
import org.dd_healthcare.internal_logcat.domain.lifecycle.OnLifeCycleEvent
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.local.SharedPreferencesImpl
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.DashboardComponent
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.SharedLogger
import org.dd_healthcare.internal_logcat.utils.StateClass
import org.dd_healthcare.internal_logcat.utils.fixedSp
import org.ncgroup.kscan.BarcodeFormat
import org.ncgroup.kscan.BarcodeResult
import org.ncgroup.kscan.ScannerView
import qrscanner.QrScanner
import kotlin.collections.get

@Composable
fun DashboardScreen(component: DashboardComponent) {

    val sharedPreferences: SharedPreferencesImpl = getKoin().get()
    val state by component.deviceListResponse.collectAsState()
    var clickInOutButton by remember { mutableStateOf(false) }

    // for qr scanning
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    val scope = rememberCoroutineScope()
    BindEffect(controller)

    // best practice fir use common lifecycle
    OnLifeCycleEvent { event ->
        if (event == Lifecycle.Event.ON_RESUME) component.deviceListApi()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(AppColors.whiteColor).padding(
            WindowInsets.safeDrawing.asPaddingValues()
        )
    ) {

        val maxHeight = maxHeight
        val maxWidth = maxWidth

        Column(
            modifier = Modifier.height(maxHeight).width(maxWidth)
                .padding(horizontal = maxWidth * 0.05f)
        ) {

            Spacer(modifier = Modifier.height(maxHeight * 0.005f))

            Row(
                modifier = Modifier.fillMaxWidth().height(maxHeight * 0.06f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.header_text),
                    color = AppColors.textGrey,
                    fontSize = fixedSp(maxHeight * 0.022f),
                    fontFamily = FontFamily(Font(Res.font.rem_semibold)),
                )

                Image(
                    painter = painterResource(Res.drawable.log_out_logo),
                    contentDescription = "Logout Icon",
                    modifier = Modifier.height(maxHeight * 0.05f).width(maxWidth * 0.10f).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        sharedPreferences.clear()
                        sharedPreferences.saveLoginState(false)
                        component.navigateToLogin()
                    }
                )
            }

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {

                when (state) {

                    is StateClass.UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center).height(maxHeight * 0.10f)
                                .width(maxWidth * 0.2f),
                            color = AppColors.themeGreenColor
                        )
                    }

                    is StateClass.UiState.Error -> {
                        Text(
                            text = stringResource(Res.string.no_data_text),
                            color = AppColors.textGrey,
                            fontSize = fixedSp(maxHeight * 0.02f),
                            fontFamily = FontFamily(Font(Res.font.rem_medium)),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    StateClass.UiState.Idle -> {
                        Text(
                            text = stringResource(Res.string.no_data_text),
                            color = AppColors.textGrey,
                            fontSize = fixedSp(maxHeight * 0.02f),
                            fontFamily = FontFamily(Font(Res.font.rem_medium)),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is StateClass.UiState.Success -> {
                        val deviceList = (state as StateClass.UiState.Success).data.data

                        if (deviceList.isEmpty()) {
                            Text(
                                text = stringResource(Res.string.no_data_text),
                                color = AppColors.textGrey,
                                fontSize = fixedSp(maxHeight * 0.02f),
                                fontFamily = FontFamily(Font(Res.font.rem_medium)),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {

                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(deviceList) { singleDevice ->

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(maxHeight * 0.12f)
                                            .padding(
                                                top = maxHeight * 0.01f,
                                                bottom = maxHeight * 0.01f
                                            ),
                                        shape = RoundedCornerShape(20.dp),
                                        backgroundColor = AppColors.editTextColor
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = maxWidth * 0.03f),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Image(
                                                painter = painterResource(Res.drawable.description_logo),
                                                contentDescription = "Description Icon",
                                                modifier = Modifier.height(maxHeight * 0.055f)
                                                    .width(maxWidth * 0.10f)
                                            )

                                            // text in middle
                                            Column(
                                                modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = maxWidth * 0.03f),
                                                verticalArrangement = Arrangement.Center
                                            ) {

                                                val typeText = buildAnnotatedString {
                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = AppColors.textGrey,
                                                            fontSize = fixedSp(maxHeight * 0.016f),
                                                            fontFamily = FontFamily(
                                                                Font(
                                                                    Res.font.rem_regular
                                                                )
                                                            )
                                                        )
                                                    ) {
                                                        append("Type : ")
                                                    }

                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = AppColors.textGrey,
                                                            fontSize = fixedSp(maxHeight * 0.019f),
                                                            fontFamily = FontFamily(
                                                                Font(
                                                                    Res.font.rem_semibold
                                                                )
                                                            )
                                                        )
                                                    ) {
                                                        append(singleDevice.productionDepartment[0].type)
                                                    }
                                                }
                                                Text(text = typeText)

                                                // second text
                                                val serialNumberText = buildAnnotatedString {
                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = AppColors.textGrey,
                                                            fontSize = fixedSp(maxHeight * 0.016f),
                                                            fontFamily = FontFamily(
                                                                Font(
                                                                    Res.font.rem_regular
                                                                )
                                                            )
                                                        )
                                                    ) {
                                                        append("S.No : ")
                                                    }

                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = AppColors.textGrey,
                                                            fontSize = fixedSp(maxHeight * 0.019f),
                                                            fontFamily = FontFamily(
                                                                Font(
                                                                    Res.font.rem_semibold
                                                                )
                                                            )
                                                        )
                                                    ) {
                                                        append(singleDevice.productionDepartment[0].serialNumber)
                                                    }
                                                }
                                                Text(text = serialNumberText)
                                            }

                                            Image(
                                                painter = painterResource(Res.drawable.edit_logo),
                                                contentDescription = "Edit Icon",
                                                modifier = Modifier.height(maxHeight * 0.055f)
                                                    .width(maxWidth * 0.06f).clickable(
                                                        indication = null,
                                                        interactionSource = remember { MutableInteractionSource() }
                                                    ) {
                                                        component.sendDataToFormPage(
                                                            singleDevice.id,
                                                            isNewDevice = false
                                                        )
                                                    }
                                            )

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (sharedPreferences.getUserType() == "Production") {
            Column(
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(end = maxWidth * 0.04f, bottom = maxHeight * 0.01f)
            ) {
                Image(
                    painter = painterResource(Res.drawable.in_logo),
                    contentDescription = "In Icon",
                    modifier = Modifier.height(maxHeight * 0.075f).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {

                        // check camera permissions here
                        scope.launch {
                            runCatching {
                                controller.providePermission(Permission.CAMERA)
                            }.onFailure {
                                SharedLogger.i("Camera Permission Failed Due to :$it")
                            }

                            // now we have camera permissions we can continue now
                            clickInOutButton = !clickInOutButton
                        }
                    }
                )

                Image(
                    painter = painterResource(Res.drawable.out_logo),
                    contentDescription = "Out Icon",
                    modifier = Modifier.height(maxHeight * 0.075f).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // check camera permissions here
                        scope.launch {
                            runCatching {
                                controller.providePermission(Permission.CAMERA)
                            }.onFailure {
                                SharedLogger.i("Camera Permission Failed Due to :$it")
                            }

                            // now we have camera permissions we can continue now
                            clickInOutButton = !clickInOutButton
                        }
                    }
                )
            }
        }
    }


    if (clickInOutButton) {
        ScannerView(
            codeTypes = listOf(
                BarcodeFormat.FORMAT_QR_CODE,
            )
        ) { result ->
            when (result) {
                is BarcodeResult.OnSuccess -> {
                    SharedLogger.i("Barcode: ${result.barcode.data}, format: ${result.barcode.format}")
                    component.sendDataToFormPage(result.barcode.data, isNewDevice = true)
                    clickInOutButton = false
                }
                is BarcodeResult.OnFailed -> {
                    SharedLogger.i("error: ${result.exception.message}")
                }
                BarcodeResult.OnCanceled -> {
                    SharedLogger.i("scan canceled")
                }
            }
        }
    }


}

