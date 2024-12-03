import SwiftUI
import shared

@main
struct iOSApp: App {

    init(){SetupKoinDiKt.doInitKoin()}

	var body: some Scene {
		WindowGroup {
			HomeView()
		}
	}
}
