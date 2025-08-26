package org.dd_healthcare.internal_logcat.presentation.di

import org.koin.core.context.startKoin

public fun doInitKoin() = startKoin {
    modules(appModule)
}