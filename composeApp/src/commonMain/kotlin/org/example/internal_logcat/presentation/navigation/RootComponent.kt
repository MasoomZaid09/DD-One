package org.example.internal_logcat.presentation.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.DashboardComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.LoginComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.SplashComponent

interface RootComponent {
    val childStack: Value<ChildStack<ChildConfig, Child>>

    sealed class Child {
        class Splash(val component: SplashComponent) : Child()
        class Login(val component: LoginComponent) : Child()
        class Dashboard(val component: DashboardComponent) : Child()
        class Form(val component: FormComponent) : Child()
    }

    sealed class ChildConfig : Parcelable {
        @Parcelize
        object Splash : ChildConfig()
        @Parcelize
        object Login : ChildConfig()
        @Parcelize
        object Dashboard : ChildConfig()
        @Parcelize
        data class Form(val data:String,val isNewDevice:Boolean) : ChildConfig()
    }
}
