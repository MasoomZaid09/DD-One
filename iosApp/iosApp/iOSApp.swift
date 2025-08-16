import SwiftUI
import ComposeApp
import UIKit

@main
struct iOSApp: App {

    init() {
        KoinKt.doInitKoin() // Initialize Koin from shared Kotlin module
    }

    var body: some Scene {
        WindowGroup {
            ComposeView()
                .ignoresSafeArea() // optional, full screen
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        // Use the MainViewController from ComposeApp Kotlin shared module
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Nothing needed here for now
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}
