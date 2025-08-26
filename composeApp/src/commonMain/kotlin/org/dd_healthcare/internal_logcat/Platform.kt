package org.dd_healthcare.internal_logcat


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform