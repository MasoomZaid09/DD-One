package org.example.internal_logcat.presentation.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.rem_regular
import org.jetbrains.compose.resources.Font
import org.example.internal_logcat.utils.AppColors

@Composable
fun errorText(error:String){

    Text(
        text = error,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resource = Res.font.rem_regular)),
        color = AppColors.errorColor)
}