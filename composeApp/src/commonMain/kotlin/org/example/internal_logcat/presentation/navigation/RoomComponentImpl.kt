package org.example.internal_logcat.presentation.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import org.example.internal_logcat.domain.local.SharedPreferencesImpl
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.DashboardComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.FormComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.LoginComponent
import org.example.internal_logcat.presentation.ui.components_or_viewmodels.SplashComponent

class RootComponentImpl(
    componentContext: ComponentContext,
    private val settings: SharedPreferencesImpl
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.ChildConfig>()

    override val childStack: Value<ChildStack<RootComponent.ChildConfig, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialStack = { listOf(RootComponent.ChildConfig.Splash) },
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(
        config: RootComponent.ChildConfig,
        context: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is RootComponent.ChildConfig.Splash -> RootComponent.Child.Splash(
                SplashComponent(
                    context,
                    navigateToLogin = {
                        navigation.replaceAll(if (settings.getLoginState()) RootComponent.ChildConfig.Dashboard else RootComponent.ChildConfig.Login)
                    })
            )

            is RootComponent.ChildConfig.Login -> RootComponent.Child.Login(
                LoginComponent(
                    context,
                    navigateToDashboard = {
                        navigation.replaceAll(RootComponent.ChildConfig.Dashboard)
                    })
            )

            is RootComponent.ChildConfig.Form -> RootComponent.Child.Form(
                FormComponent(
                    context,
                    navigateToDashboard = {
                        navigation.replaceAll(RootComponent.ChildConfig.Dashboard)
                    },
                    navigateBack = { navigation.pop() }, config.data , config.isNewDevice
                )
            )

            is RootComponent.ChildConfig.Dashboard -> RootComponent.Child.Dashboard(
                DashboardComponent(context, navigateToLogin = {
                    navigation.replaceAll(RootComponent.ChildConfig.Login)
                }, goToFormPage = { data,isNew ->
                    navigation.push(RootComponent.ChildConfig.Form(data,isNew))
                })
            )
        }
    }
}