package org.dd_healthcare.internal_logcat.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun getCurrentDateTime(): String {
    val nowInstant = Clock.System.now()
    val currentDateTime = nowInstant.toLocalDateTime(TimeZone.currentSystemDefault())

    return currentDateTime.date.toString()
}