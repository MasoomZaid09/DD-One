package org.dd_healthcare.internal_logcat.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.keyboardSafeArea(): Modifier = this
    .consumeWindowInsets(WindowInsets.ime) // prevent shrink
    .padding(
        WindowInsets.safeDrawing
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
            .asPaddingValues()
    )
    .padding(
        WindowInsets.ime
            .only(WindowInsetsSides.Bottom)
            .asPaddingValues()
    )

