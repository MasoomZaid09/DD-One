package org.example.internal_logcat

import android.os.Build
import com.mohamedrejeb.calf.core.PlatformContext

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
