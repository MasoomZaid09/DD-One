package org.dd_healthcare.internal_logcat.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.rem_medium
import internallogcat.composeapp.generated.resources.rem_regular
import org.jetbrains.compose.resources.Font
import org.dd_healthcare.internal_logcat.utils.AppColors
import org.dd_healthcare.internal_logcat.utils.fixedSp

@Composable
fun InfoLoggingFormStatus(statusNumber: Int, maxHeight: Dp,maxWidth: Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(maxHeight * 0.10f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle with Text inside
        Box(
            modifier = Modifier.fillMaxHeight().width(maxWidth * 0.20f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = (statusNumber * 0.25f), // 25% per statusNumber
                modifier = Modifier.fillMaxSize(),
                color = AppColors.themeGreenColor,
                strokeWidth = 5.dp
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Status",
                    fontSize = fixedSp(maxHeight * 0.015f),
                    color = AppColors.textGrey,
                    fontFamily = FontFamily(Font(Res.font.rem_medium))
                )
                Text(
                    text = "$statusNumber of 4",
                    fontSize = fixedSp(maxHeight * 0.02f),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(Res.font.rem_medium))
                )
            }
        }

        Spacer(modifier = Modifier.width(maxWidth * 0.05f))

        // Texts on the right
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().width(maxWidth * 0.75f)
        ) {
            Text(
                text = "INFORMATION LOGGING FORM",
                fontSize = fixedSp(maxHeight * 0.02f),
                color = AppColors.themeGreenColor,
                fontFamily = FontFamily(Font(Res.font.rem_medium))
            )
            Text(
                text = "This Form Ensures Proper Tracking Of Device\nLifecycle From Production To Post-Delivery Service.",
                fontSize = fixedSp(maxHeight * 0.015f),
                color = AppColors.textGrey,
                fontFamily = FontFamily(Font(Res.font.rem_regular))
            )
        }
    }
}
