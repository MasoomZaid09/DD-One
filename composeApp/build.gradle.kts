import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinCocoapods)
    kotlin("plugin.parcelize")
}

kotlin {

    cocoapods {
        summary = "Some description for the Shared Module"
        version = "1.0"
        homepage= "Line to the shared Module homepage"
        // ios depoloymentTarget should be match with PodFile located in IosApp folder
        ios.deploymentTarget = "15.4"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
            export("com.mohamedrejeb.calf:calf-ui:0.8.0")
        }

        // if any libs we use we write
        // for example googlemap
//        pod("GoogleMap") {
//            version = libs.versions.nameofThatLibraryVersionvariable.get()
//            extraOpts += listOf("-compiler-option","-fmodules")
//        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // must have to run on ios
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
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

                // calf for use view like file picker
                // File picker ke liye
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
                implementation(compose.material3)
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
    namespace = "org.example.internal_logcat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.internal_logcat"
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


