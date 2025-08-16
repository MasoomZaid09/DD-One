// shared/src/commonMain/kotlin/Callbacks.kt
package com.example.lifecycle

import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnStop

fun observeLifecycle(lifecycle: LifecycleRegistry) {
    lifecycle.doOnResume {
        println("Resumed!")
        // yahan apna code likho jo resume pe run hoga
    }

    lifecycle.doOnStop {
        println("Stopped!")
        // yahan apna code likho jo stop pe run hoga
    }
}
