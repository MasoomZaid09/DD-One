package org.example.internal_logcat.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


// We have to pass CIO for android and Darwin for ios but
// if don't pass anything ktor use automatically
expect fun createHttpClient(): HttpClient