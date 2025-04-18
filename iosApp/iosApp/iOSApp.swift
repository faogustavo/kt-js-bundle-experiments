import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        SharedKoin.shared.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
