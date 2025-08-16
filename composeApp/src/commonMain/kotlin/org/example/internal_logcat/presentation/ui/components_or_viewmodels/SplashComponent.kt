package org.example.internal_logcat.presentation.ui.components_or_viewmodels

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashComponent(private val componentContext: ComponentContext, private val navigateToLogin : () -> Unit ) : ComponentContext by componentContext {

    init {
        CoroutineScope(Dispatchers.Main).launch{
            delay(3000L)
            navigateToLogin()
        }
    }
}