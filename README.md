# Alfie-KMP

Alfie is a Kotlin Multiplatform application supporting both Android and iOS platforms. This project is built using the Kotlin Multiplatform (KMP) template structure and includes shared business logic and UI implementation using Compose Multiplatform.

## Technologies

This app uses the following multiplatform dependencies:

- [Compose Multiplatform](https://jb.gg/compose) for UI
- [Compose Navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)
- [Ktor](https://ktor.io/) for networking
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON handling
- [Coil](https://github.com/coil-kt/coil) for image loading
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection

## Project Structure

- `/composeApp` - Kotlin Multiplatform module containing shared code
  - `/commonMain` - Shared code across all platforms
  - `/androidMain` - Android-specific code
  - `/iosMain` - iOS-specific code
- `/iosApp` - iOS application (SwiftUI wrapper)

## Getting Started

### Prerequisites

- JDK 17 or later
- Android Studio Ladybug or later (for Android development)
- Xcode 15 or later (for iOS development, macOS only)

### Android

Run the Android app using Android Studio or run:
```bash
./gradlew :composeApp:installDebug
```

### iOS

Open `iosApp/iosApp.xcodeproj` in Xcode and run the app on a simulator or device.

## Building

To build the project:
```bash
./gradlew build
```

Note: Building requires access to Google Maven repositories for Android dependencies.

## License

See [LICENSE](LICENSE) file for details.

