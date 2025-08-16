package org.example.internal_logcat.domain.qr_scanner

import android.content.Intent
import org.example.internal_logcat.CameraXScanningActivity
import org.example.internal_logcat.DDApplicationClass

actual fun launchQrScanner(onResult : (String) -> Unit){
    CameraXScanningActivity.onScanned = onResult
    val context = DDApplicationClass.contextForApplication
    val intent = Intent()
    intent.setClassName(context!!,"org.example.internal_logcat.CameraXScanningActivity")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}