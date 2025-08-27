package org.dd_healthcare.internal_logcat.domain.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
actual fun hideKeyboard() {
    val controller = LocalSoftwareKeyboardController.current
    controller?.hide()
}