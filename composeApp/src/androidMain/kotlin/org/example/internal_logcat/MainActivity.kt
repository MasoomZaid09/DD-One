package org.example.internal_logcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.lifecycle.observeLifecycle
import org.example.internal_logcat.domain.lifecycle.createSharedLifecycle
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.example.internal_logcat.presentation.navigation.RootComponent
import org.example.internal_logcat.presentation.ui.App

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: RootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Required to use Decompose
        val rootContext = defaultComponentContext()

        // 💉 Inject the component properly using Koin parameters
        rootComponent = inject<RootComponent> {
            parametersOf(rootContext)
        }.value

        setContent {
            App(rootComponent)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
}
