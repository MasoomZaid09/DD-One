package org.dd_healthcare.internal_logcat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.dd_logo
import internallogcat.composeapp.generated.resources.email_text
import internallogcat.composeapp.generated.resources.login_text
import internallogcat.composeapp.generated.resources.password_text
import internallogcat.composeapp.generated.resources.rem_bold
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_semibold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin
import org.dd_healthcare.internal_logcat.domain.local.SharedPreferencesImpl
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.LoginComponent
import org.dd_healthcare.internal_logcat.presentation.ui.composables.RoundedEditText
import org.dd_healthcare.internal_logcat.presentation.ui.composables.errorText
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.StateClass
import org.dd_healthcare.internal_logcat.utils.Validator
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun LoginScreen(component: LoginComponent) {

    val state by component.loginResponse.collectAsState()

    val sharedPreferences : SharedPreferencesImpl = getKoin().get()

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.whiteColor),
//            .pointerInput(Unit){
//                detectTapGestures { hideKeyboard() }
//            },
    ) {

        val maxWidth = maxWidth
        val maxHeight = maxHeight

        when(state){

            is StateClass.UiState.Loading ->{
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).height(maxHeight * 0.10f).width(maxWidth * 0.20f),
                    color = AppColors.themeGreenColor
                )
            }

            is StateClass.UiState.Success -> {

                // saving data in local storage for future use
                val response = (state as StateClass.UiState.Success).data.data
                sharedPreferences.saveToken(response.token)
                sharedPreferences.saveUserId(response.id)
                sharedPreferences.saveLoginState(true)
                sharedPreferences.saveUserType(response.userType)

                component.navigateToDashboard()
            }

            else -> {
                Column(

                    modifier = Modifier.height(maxHeight).width(maxWidth).padding(horizontal = maxWidth * 0.05f).verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(maxHeight * 0.18f))

                    Image(
                        painter = painterResource(Res.drawable.dd_logo),
                        contentDescription = "Centered Image",
                        modifier = Modifier
                            .height(maxHeight * 0.08f)
                            .fillMaxWidth()
                            .padding(horizontal = maxWidth * 0.25f)
                            .aspectRatio(1f)
                    )

                    Spacer(modifier = Modifier.height(maxHeight * 0.09f))

                    Text(
                        text = stringResource(Res.string.login_text),
                        color = AppColors.textGrey,
                        fontSize = fixedSp(maxHeight * 0.02f),
                        fontFamily = FontFamily(Font(Res.font.rem_semibold))
                    )

                    Spacer(modifier = Modifier.height(maxHeight * 0.03f))

                    Text(
                        text = stringResource(Res.string.email_text),
                        color = AppColors.textGrey,
                        fontSize = fixedSp(maxHeight * 0.015f),
                        fontFamily = FontFamily(Font(Res.font.rem_medium))
                    )

                    Spacer(modifier = Modifier.height(maxHeight * 0.015f))

                    RoundedEditText(
                        email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxHeight * 0.065f)
                            .clip(shape = RoundedCornerShape(20))
                            .background(AppColors.editTextColor),
                        "Enter your email",
                        (maxHeight * 0.015f)
                    )

                    emailError?.let { errorText(it,(maxHeight * 0.014f)) }

                    Spacer(modifier = Modifier.height(maxHeight * 0.025f))

                    Text(
                        text = stringResource(Res.string.password_text),
                        color = AppColors.textGrey,
                        fontSize = fixedSp(maxHeight * 0.015f),
                        fontFamily = FontFamily(Font(Res.font.rem_medium))
                    )

                    Spacer(modifier = Modifier.height(maxHeight * 0.015f))

                    RoundedEditText(
                        pass,
                        onValueChange = {
                            pass = it
                            passwordError = null
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxHeight * 0.065f)
                            .clip(shape = RoundedCornerShape(20))
                            .background(AppColors.editTextColor),
                        "Enter your password",
                        (maxHeight * 0.015f)
                    )
                    passwordError?.let { errorText(it,(maxHeight * 0.014f)) }

                    Spacer(modifier = Modifier.height(maxHeight * 0.07f))

                    Button(
                        onClick = {
                            if (!Validator.isValidEmail(email)) {
                                emailError = "Enter Your Email"
                            } else if (!Validator.isValidPassword(pass)) {
                                passwordError = "Incorrect password"
                            } else {
                                component.loginApi(email.trim().toString(), pass.trim())
                            }

                        },
                        shape = RoundedCornerShape(20),

                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColors.themeGreenColor,
                            contentColor = AppColors.whiteColor // optional
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxHeight * 0.065f)
                    ) {
                        Text(
                            text = stringResource(Res.string.login_text),
                            fontSize = fixedSp(maxHeight * 0.017f),
                            color = AppColors.whiteColor,
                            fontFamily = FontFamily(Font(Res.font.rem_bold))
                        )
                    }

                }
            }
        }
    }
}
