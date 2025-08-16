package org.example.internal_logcat.utils

import co.touchlab.kermit.Logger

object SharedLogger {
    private val logger = Logger.withTag("Masoom")

    fun d(msg: String) = logger.d { msg }
    fun i(msg: String) = logger.i { msg }
}