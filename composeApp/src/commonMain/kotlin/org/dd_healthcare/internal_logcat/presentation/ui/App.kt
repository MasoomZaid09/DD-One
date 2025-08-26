package org.dd_healthcare.internal_logcat.presentation.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import org.dd_healthcare.internal_logcat.presentation.navigation.RootComponent
import org.dd_healthcare.internal_logcat.presentation.ui.screens.DashboardScreen
import org.dd_healthcare.internal_logcat.presentation.ui.screens.FormScreen
import org.dd_healthcare.internal_logcat.presentation.ui.screens.LoginScreen
import org.dd_healthcare.internal_logcat.presentation.ui.screens.SplashScreen

@Composable
fun App(root: RootComponent) {
    Children(stack = root.childStack) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Splash -> SplashScreen(instance.component)
            is RootComponent.Child.Login -> LoginScreen(instance.component)
            is RootComponent.Child.Form -> FormScreen(instance.component)
            is RootComponent.Child.Dashboard -> DashboardScreen(instance.component)
        }
    }
}

