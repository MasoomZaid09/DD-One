package org.example.internal_logcat

import com.mohamedrejeb.calf.core.PlatformContext

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform