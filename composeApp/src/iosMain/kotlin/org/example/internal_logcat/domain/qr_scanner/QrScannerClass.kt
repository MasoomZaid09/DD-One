package org.example.internal_logcat.domain.qr_scanner

import kotlinx.cinterop.ExperimentalForeignApi
import org.example.internal_logcat.utils.SharedLogger
import platform.AVFoundation.*
import platform.SharedWithYouCore.SharedWithYouCoreVersionNumber
import platform.UIKit.*
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue

private var qrScanner: IOSQrScanner? = null

actual fun launchQrScanner(onResult: (String) -> Unit) {
    SharedLogger.i("IN IOS SECTION START")
    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return
    qrScanner = IOSQrScanner(onResult)
    qrScanner?.startScanning(rootVC)
    SharedLogger.i("IN IOS SECTION END")
}

private class IOSQrScanner(private val onResult: (String) -> Unit) :
    NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {

    private val session = AVCaptureSession()
    private var previewLayer: AVCaptureVideoPreviewLayer? = null

    @OptIn(ExperimentalForeignApi::class)
    fun startScanning(parentVC: UIViewController) {
        val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo) ?: return
        val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) ?: return
        session.addInput(input)

        val output = AVCaptureMetadataOutput()
        session.addOutput(output)
        output.setMetadataObjectsDelegate(this, dispatch_get_main_queue())
        output.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)

        previewLayer = AVCaptureVideoPreviewLayer(session = session).apply {
            videoGravity = AVLayerVideoGravityResizeAspectFill
            frame = parentVC.view.bounds
        }

        previewLayer?.let { parentVC.view.layer.addSublayer(it) }
        session.startRunning()
    }

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        val qrCode = didOutputMetadataObjects.firstOrNull() as? AVMetadataMachineReadableCodeObject
        val value = qrCode?.stringValue
        if (value != null) {
            session.stopRunning()
            previewLayer?.removeFromSuperlayer()
            onResult(value)
        }
    }
}
