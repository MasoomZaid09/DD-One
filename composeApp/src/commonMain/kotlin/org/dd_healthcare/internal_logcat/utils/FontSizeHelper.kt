package org.dd_healthcare.internal_logcat.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

// create this method to ignore system font sizes and use fixed font sizes
@Composable
fun fixedSp(value: Dp): TextUnit {
    val density = LocalDensity.current
    return with(density){
        value.value.dp.toSp()
    }
}