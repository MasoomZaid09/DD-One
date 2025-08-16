package org.example.internal_logcat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform