package org.dd_healthcare.internal_logcat

import android.app.Application
import android.content.Context
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.dd_healthcare.internal_logcat.presentation.di.appModule

class DDApplicationClass : Application() {

    companion object {
        var contextForApplication: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        Logger.setLogWriters(LogcatWriter())

        contextForApplication = applicationContext

        startKoin{
            androidContext(this@DDApplicationClass)
            modules(appModule, androidModule)
        }
    }

}