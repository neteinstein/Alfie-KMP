# Alfie-KMP

Alfie is a Kotlin Multiplatform (KMP) application demonstrating modern cross-platform development with shared business logic and UI across Android and iOS platforms using Compose Multiplatform.

## 📋 Table of Contents

- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technologies & Dependencies](#technologies--dependencies)
- [Getting Started](#getting-started)
- [Building & Running](#building--running)
- [Code Quality & Standards](#code-quality--standards)
- [License](#license)

## 🏗 Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

### Architecture Layers

#### 1. **Presentation Layer (UI)**
- **Location**: `composeApp/src/commonMain/kotlin/com/neteinstein/alfie/screens/`
- **Pattern**: MVVM (Model-View-ViewModel)
- **Components**:
  - **Screens**: Composable UI components (ListScreen, DetailScreen)
  - **ViewModels**: Handle UI logic and state management
  - **State**: Immutable UI state using Kotlin StateFlow
- **Principles**:
  - State hoisting for composables
  - Lifecycle-aware state consumption
  - Unidirectional data flow

#### 2. **Domain Layer**
- **Location**: `composeApp/src/commonMain/kotlin/com/neteinstein/alfie/data/`
- **Components**:
  - **MuseumRepository**: Coordinates data operations and caching
  - **MuseumObject**: Domain model representing business entities
- **Principles**:
  - Pure business logic
  - Platform-agnostic
  - No framework dependencies

#### 3. **Data Layer**
- **Location**: `composeApp/src/commonMain/kotlin/com/neteinstein/alfie/data/`
- **Components**:
  - **MuseumApi**: Network interface (abstraction)
  - **KtorMuseumApi**: Ktor implementation for HTTP requests
  - **MuseumStorage**: Local storage interface (abstraction)
  - **InMemoryMuseumStorage**: In-memory caching implementation
- **Principles**:
  - Repository pattern for data access
  - Thread-safe implementations with coroutine context switching
  - Reactive data flow using Kotlin Flow
  - Separation of remote and local data sources

#### 4. **Dependency Injection**
- **Location**: `composeApp/src/commonMain/kotlin/com/neteinstein/alfie/di/`
- **Framework**: Koin
- **Modules**:
  - `dataModule`: Provides data layer dependencies
  - `viewModelModule`: Provides ViewModel instances
- **Principles**:
  - Constructor injection
  - Single source of truth for dependency configuration

### Design Decisions

1. **MVVM Pattern**: Chosen for its clear separation between UI and business logic, excellent support in Compose Multiplatform, and lifecycle-aware state management.

2. **Repository Pattern**: Abstracts data sources, enabling easy testing and future migration to different storage solutions.

3. **Reactive Streams (Flow)**: Used for continuous data updates and automatic UI refreshing when data changes.

4. **Dependency Injection with Koin**: Lightweight, Kotlin-first DI framework that works seamlessly across platforms without reflection.

5. **Immutable State**: All UI state is immutable, preventing unintended side effects and making state management predictable.

## 📁 Project Structure

```
Alfie-KMP/
├── composeApp/                    # Shared KMP module
│   ├── src/
│   │   ├── commonMain/           # Shared code for all platforms
│   │   │   ├── kotlin/
│   │   │   │   └── com/neteinstein/alfie/
│   │   │   │       ├── App.kt                    # Main app entry point & navigation
│   │   │   │       ├── data/                     # Data layer
│   │   │   │       │   ├── MuseumApi.kt         # Network API interface
│   │   │   │       │   ├── MuseumObject.kt      # Domain model
│   │   │   │       │   ├── MuseumRepository.kt  # Repository pattern implementation
│   │   │   │       │   └── MuseumStorage.kt     # Storage interface & implementation
│   │   │   │       ├── di/                       # Dependency injection
│   │   │   │       │   └── Koin.kt              # DI configuration
│   │   │   │       └── screens/                  # UI layer (Presentation)
│   │   │   │           ├── list/                 # List feature
│   │   │   │           │   ├── ListScreen.kt    # UI composable
│   │   │   │           │   └── ListViewModel.kt # ViewModel
│   │   │   │           └── detail/               # Detail feature
│   │   │   │               ├── DetailScreen.kt  # UI composable
│   │   │   │               └── DetailViewModel.kt # ViewModel
│   │   │   └── composeResources/        # Shared resources
│   │   ├── androidMain/              # Android-specific code
│   │   │   ├── kotlin/
│   │   │   │   └── com/neteinstein/alfie/
│   │   │   │       ├── MainActivity.kt          # Android entry point
│   │   │   │       └── MuseumApp.kt            # Application class
│   │   │   ├── AndroidManifest.xml
│   │   │   └── res/                  # Android resources
│   │   └── iosMain/                  # iOS-specific code
│   │       └── kotlin/
│   │           └── com/neteinstein/alfie/
│   │               └── MainViewController.kt    # iOS entry point
│   └── build.gradle.kts              # Module build configuration
├── iosApp/                           # iOS native wrapper
│   ├── iosApp/
│   │   ├── iOSApp.swift             # iOS app entry
│   │   └── ContentView.swift        # SwiftUI integration
│   ├── Configuration/
│   │   └── Config.xcconfig          # iOS configuration
│   └── iosApp.xcodeproj/            # Xcode project
├── gradle/                           # Gradle wrapper & dependencies
│   ├── libs.versions.toml           # Version catalog
│   └── wrapper/
├── build.gradle.kts                  # Root build configuration
├── settings.gradle.kts               # Project settings
└── README.md                         # This file
```

## 🛠 Technologies & Dependencies

### Core Technologies

| Technology | Version | Purpose | Justification |
|------------|---------|---------|---------------|
| **Kotlin** | 2.2.0 | Programming language | Latest stable, multiplatform support |
| **Compose Multiplatform** | 1.8.2 | UI framework | Shared UI across platforms, declarative UI |
| **Gradle** | 8.13 | Build system | Industry standard, KMP support |

### Libraries

#### UI & Navigation
- **Compose Material3**: Modern Material Design components
- **Navigation Compose** (2.9.0-beta03): Type-safe navigation with kotlinx.serialization
- **Lifecycle Runtime Compose** (2.9.1): Lifecycle-aware components
- **Material Icons Core** (1.7.3): Material Design icons

#### Networking & Serialization
- **Ktor Client** (3.1.3): Multiplatform HTTP client
  - `ktor-client-core`: Core client functionality
  - `ktor-client-okhttp`: Android HTTP engine
  - `ktor-client-darwin`: iOS HTTP engine
  - `ktor-client-content-negotiation`: Content negotiation support
  - `ktor-serialization-kotlinx-json`: JSON serialization integration
- **kotlinx.serialization**: Type-safe JSON parsing

#### Dependency Injection
- **Koin** (4.1.0): Lightweight DI framework
  - `koin-core`: Core DI functionality
  - `koin-compose-viewmodel`: ViewModel integration for Compose

#### Image Loading
- **Coil 3** (3.2.0): Efficient image loading
  - `coil-compose`: Compose integration
  - `coil-network-ktor`: Ktor networking backend

#### Android-Specific
- **Activity Compose** (1.10.1): Jetpack Compose integration
- **Android Gradle Plugin** (8.3.0): Build tooling

### Dependency Justification

All dependencies are:
- ✅ **Up-to-date**: Using latest stable versions (or latest beta for Navigation Compose which provides type-safe navigation)
- ✅ **Necessary**: Each serves a specific purpose in the architecture
- ✅ **Well-maintained**: Official libraries or widely-adopted community solutions
- ✅ **Multiplatform**: Support both Android and iOS where applicable

### Version Management

Dependencies are managed using Gradle Version Catalog (`gradle/libs.versions.toml`) for:
- Centralized version control
- Type-safe dependency declarations
- Easy version updates across modules

## 🚀 Getting Started

### Prerequisites

#### Required
- **JDK 17 or later**: For Kotlin compilation
- **Gradle 8.13**: Included via Gradle wrapper (`./gradlew`)

#### Platform-Specific
- **For Android Development**:
  - Android Studio Ladybug (2024.2.1) or later
  - Android SDK with API level 35
  - Minimum device: API 24 (Android 7.0)

- **For iOS Development** (macOS only):
  - Xcode 15 or later
  - macOS 13.0 or later
  - iOS Simulator or physical device

### Initial Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/neteinstein/Alfie-KMP.git
   cd Alfie-KMP
   ```

2. **Verify Gradle setup**:
   ```bash
   ./gradlew --version
   ```

## 🏃 Building & Running

### Android

#### Option 1: Android Studio
1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Select the `composeApp` configuration
4. Click Run or press `Shift + F10`

#### Option 2: Command Line
```bash
# Debug build
./gradlew :composeApp:assembleDebug

# Install on connected device
./gradlew :composeApp:installDebug

# Run on connected device
./gradlew :composeApp:installDebug && adb shell am start -n com.neteinstein.alfie/.MainActivity
```

### iOS

#### Option 1: Xcode
1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select a simulator or connected device
3. Click Run or press `Cmd + R`

#### Option 2: Command Line
```bash
# Build the Kotlin framework
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64

# Open in Xcode
open iosApp/iosApp.xcodeproj
```

### Building Both Platforms

```bash
# Complete build for all platforms
./gradlew build

# Clean build
./gradlew clean build
```

**Note**: Building requires access to Google Maven repositories for Android dependencies and Apple Developer account for iOS device deployment.

## ✅ Code Quality & Standards

This project adheres to the following standards:

### Architecture & Design
✅ Clear separation of concerns (UI, Domain, Data layers)  
✅ MVVM architectural pattern  
✅ Repository pattern for data access  
✅ Dependency Injection with Koin  
✅ Strict layer boundaries preventing internal implementation exposure  

### Code Quality
✅ Idiomatic Kotlin (data classes, sealed classes, coroutines, flows)  
✅ Null safety throughout  
✅ Immutable data structures  
✅ Consistent naming conventions (camelCase for properties, PascalCase for classes)  
✅ Meaningful comments for complex logic  
✅ No code smells (god classes, long methods avoided)  

### UI/UX
✅ Responsive design supporting different screen sizes  
✅ Loading, error, and empty states handled  
✅ Lifecycle-aware state management  
✅ State hoisting principles applied  
✅ Smooth navigation with type-safe routing  

### Data Layer
✅ Proper HTTP error handling  
✅ In-memory caching strategy  
✅ Coroutines for async operations  
✅ Thread-safe implementations  
✅ Reactive streams with Kotlin Flow  
✅ Type-safe JSON parsing with kotlinx.serialization  

### Build Configuration
✅ Gradle version catalog for dependency management  
✅ Up-to-date dependencies  
✅ Proper .gitignore (no build artifacts, IDE files)  
✅ Modular project structure  

### Performance
✅ Efficient image loading with Coil  
✅ Proper background task handling  
✅ Lifecycle-aware components  
✅ StateFlow with WhileSubscribed strategy for efficient resource usage  

## 📄 License

See [LICENSE](LICENSE) file for details.

