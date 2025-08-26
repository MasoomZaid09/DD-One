import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    kotlin("plugin.parcelize")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    listOf(iosX64(),iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.decompose)
                implementation(libs.zxing.android.embedded)
                implementation(libs.androidx.activity.ktx)
            }
        }

        val commonMain by getting {
            resources.srcDir("src/commonMain/resources")
            dependencies {

//                // calf for use view like file picker
//                // File picker ke liye
                implementation(libs.calf.file.picker)

                // Agar tumne calf-ui components ka use kiya hai to
                implementation(libs.calf.ui)

                // COMPOSE MULTIPLATFORM QR SCANNER
                implementation(libs.qr.kit)
                implementation(libs.calf.file.picker.coil)
                implementation(libs.calf.file.picker)
                implementation(libs.kmp.date.time.picker)

                implementation(libs.multiplatform.settings.no.arg)
                implementation(libs.multiplatform.settings)
                implementation(libs.kermit)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
//                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.decompose)
                implementation(libs.decompose.jetbrains)

                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                // Serialization
                implementation(libs.serialization.json)

                // Coroutines
                implementation(libs.coroutines.core)

                // Koin
                implementation(libs.koin.core)

                // Compose
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // must have to run on ios
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies{
                implementation(libs.ktor.client.darwin)
            }
            iosX64().compilations["main"].defaultSourceSet.dependsOn(this)
            iosArm64().compilations["main"].defaultSourceSet.dependsOn(this)
            iosSimulatorArm64().compilations["main"].defaultSourceSet.dependsOn(this)
        }

    }

}

android {
    namespace = "org.dd_healthcare.internal_logcat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.dd_healthcare.internal_logcat"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencies {
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ui.android)
    debugImplementation(compose.uiTooling)
}


