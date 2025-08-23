# Internal Production, Dispatch & Service Management (KMM)

A Kotlin Multiplatform (KMM) project for internal company operations — production tracking, dispatch scheduling, and service management — with shared business logic and UI for Android and iOS.

## 📸 Screenshots

<img width="1024" height="568" alt="cover 1" src="https://github.com/user-attachments/assets/52694103-6f54-478d-a118-a383992012e9" />

## 🚀 Features
- Shared **UI logic** with [Decompose](https://github.com/arkivanov/Decompose)
- API integration via [Ktor](https://ktor.io)
- Dependency injection using [Koin](https://insert-koin.io)
- Modular architecture for production, dispatch, and service flows
- Common codebase for Android & iOS

## 🛠 Tech Stack
- **Kotlin Multiplatform**: Shared logic & UI components
- **Ktor Client**: Networking
- **Koin**: Dependency injection
- **Decompose**: Component-based UI architecture
- **Coroutines**: Asynchronous programming

## 📦 Modules
- **:shared** → Common logic, models, repositories, UI components
- **:androidApp** → Android-specific UI (Jetpack Compose or XML)
- **:iosApp** → iOS-specific integration (SwiftUI/UIKit)
