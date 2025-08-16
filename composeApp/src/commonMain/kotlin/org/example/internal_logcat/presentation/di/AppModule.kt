package org.example.internal_logcat.presentation.di

import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.example.internal_logcat.domain.local.SharedPreferencesImpl
import org.example.internal_logcat.data.local.createSharedPreferences
import org.example.internal_logcat.data.remote.DDApi
import org.example.internal_logcat.data.remote.DDApiImpl
import org.example.internal_logcat.data.remote.createHttpClient
import org.example.internal_logcat.data.repo.DDRepositoryImpl
import org.example.internal_logcat.domain.repo.DDRepository
import org.example.internal_logcat.domain.usecases.DDUseCase
import org.example.internal_logcat.presentation.navigation.RootComponent
import org.example.internal_logcat.presentation.navigation.RootComponentImpl

val appModule = module {

    single<HttpClient> {
        createHttpClient()
    }

    single<DDApi> {
        DDApiImpl(get())
    }

    single<DDRepository> { DDRepositoryImpl(get()) }

    single { DDUseCase(get()) }

    single<Settings> { createSharedPreferences() }

    single { SharedPreferencesImpl(get()) }


    factory<RootComponent> { (componentContext: ComponentContext) ->
        RootComponentImpl(
            componentContext = componentContext,
            settings = get()
        )
    }
}