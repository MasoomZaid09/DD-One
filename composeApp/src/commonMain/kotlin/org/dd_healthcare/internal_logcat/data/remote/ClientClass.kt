package org.dd_healthcare.internal_logcat.data.remote

import io.ktor.client.HttpClient


// We have to pass CIO for android and Darwin for ios but
// if don't pass anything ktor use automatically
expect fun createHttpClient(): HttpClient