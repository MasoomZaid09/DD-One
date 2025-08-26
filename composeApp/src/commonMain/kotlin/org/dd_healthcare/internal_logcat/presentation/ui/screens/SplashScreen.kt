package org.dd_healthcare.internal_logcat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import internallogcat.composeapp.generated.resources.Res
import internallogcat.composeapp.generated.resources.dd_logo
import org.jetbrains.compose.resources.painterResource
import org.dd_healthcare.internal_logcat.presentation.ui.components_or_viewmodels.SplashComponent

@Composable
fun SplashScreen(component: SplashComponent) {
    return BoxWithConstraints (
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.dd_logo),
            contentDescription = "Centered Image",
            modifier = Modifier.height(maxHeight * 0.25f).width(maxWidth * 0.40f)
        )
    }
}