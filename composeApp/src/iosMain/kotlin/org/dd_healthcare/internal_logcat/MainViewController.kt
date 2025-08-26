package org.dd_healthcare.internal_logcat

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.dd_healthcare.internal_logcat.presentation.navigation.RootComponent
import org.dd_healthcare.internal_logcat.presentation.ui.App
import platform.UIKit.UIViewController

class IOSRootProvider(val lifecycle: Lifecycle) : KoinComponent {
    fun getRoot(): RootComponent {
        val rootContext = DefaultComponentContext(lifecycle = lifecycle)
        return get { parametersOf(rootContext) }
    }
}

fun MainViewController(): UIViewController {
    val lifecycleRegistry =LifecycleRegistry()
    return ComposeUIViewController {
        App(IOSRootProvider(lifecycleRegistry).getRoot())
    }
}


