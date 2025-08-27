package org.dd_healthcare.internal_logcat.presentation.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.rem_regular
import org.jetbrains.compose.resources.Font
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun errorText(error:String,fontSize: Dp){
    Text(
        text = error,
        fontSize = fixedSp(fontSize),
        fontFamily = FontFamily(Font(resource = Res.font.rem_regular)),
        color = AppColors.errorColor)
}