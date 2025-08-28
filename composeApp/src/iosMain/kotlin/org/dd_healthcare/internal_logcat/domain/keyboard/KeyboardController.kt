package org.dd_healthcare.internal_logcat.domain.keyboard

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
actual fun hideKeyboard(){
    UIApplication.sharedApplication.sendAction(
        NSSelectorFromString("resignFirstResponder"),
        to = null,
        from = null,
        forEvent = null,
    )
}