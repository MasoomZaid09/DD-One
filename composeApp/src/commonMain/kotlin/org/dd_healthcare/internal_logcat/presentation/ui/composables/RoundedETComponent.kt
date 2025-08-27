package org.dd_healthcare.internal_logcat.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.rem_regular
import org.jetbrains.compose.resources.Font
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.fixedSp


@Composable
fun RoundedEditText(value : String,onValueChange: (String) -> Unit, modifier: Modifier , placeHolderText:String ,fontSize: Dp) {
    return Box(modifier = modifier){
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolderText,
                    fontSize = fixedSp(fontSize),
                    fontFamily = FontFamily(Font(Res.font.rem_regular)),
                    color = AppColors.textGrey
                )
            },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(AppColors.editTextColor),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = AppColors.themeGreenColor,
                textColor = AppColors.textGrey
            ),
            textStyle = TextStyle(
                fontSize = fixedSp(fontSize),
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            ),
            singleLine = true
        )
    }
}

@Composable
fun RoundedEditTextNormal(value : String,onValueChange: (String) -> Unit, placeHolderText:String ,fontSize: Dp = 14.dp) {
    return Box(

        contentAlignment = Alignment.Center,
        modifier = Modifier.height(50.dp).fillMaxWidth().clip(RoundedCornerShape(20)).background(
        AppColors.editTextColor
    )) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolderText,
                    fontSize = fixedSp(fontSize),
                    fontFamily = FontFamily(Font(Res.font.rem_regular)),
                    color = AppColors.textGrey
                )
            },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(AppColors.editTextColor),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = AppColors.themeGreenColor,
                textColor = AppColors.textGrey
            ),
            textStyle = TextStyle(
                fontSize = fixedSp(fontSize),
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            ),
            singleLine = true
        )
    }
}