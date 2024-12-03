# AniVerse

AniVerse is a **Kotlin Multiplatform Mobile (KMP)** application designed for both Android and iOS platforms. It utilizes modern development practices with **Clean Architecture** and **Model-View-Intent (MVI)**. AniVerse supports platform-specific native UI components while sharing the business logic.


## 📸 Screenshots

<div align="center">
  <img src="/sampleImages/android.png" width="180px" height="350px" alt="Android UI" />
  <img src="/sampleImages/ios.png" width="180px" height="350px" alt="iOS UI" />
</div>

---

## 🌟 Features

- **Cross-Platform Compatibility**: Unified shared business logic for Android and iOS.
- **Native UI**:
  - **Android**: Built using **Jetpack Compose** for a modern and declarative UI.
  - **iOS**: Uses **SwiftUI**, Apple’s native declarative UI framework.
- **Architecture**: Implements **MVI** for predictable and reactive state management.
- **Networking**: Powered by **Ktor** for efficient and flexible API communication.
- **Dependency Injection**: Modular and testable code with **Koin**.

---

## 📖 How AniVerse Leverages KMP

AniVerse maximizes the potential of Kotlin Multiplatform Mobile by sharing core business logic while maintaining platform-specific UI experiences.

### Shared Code (Business Logic)
The **shared module** contains all reusable code:
- **Data Layer**: Manages data fetching via **Ktor**, mapping, and DTOs.
- **Domain Layer**: Includes use cases, repository abstractions, and business logic.
- **Presentation Layer**: Includes intent, viewmodels, and UiState.

### Native UI for Each Platform
- **Android**: Uses **Jetpack Compose** for rich and responsive UI.
- **iOS**: Leverages **SwiftUI** to follow Apple's design guidelines.
- Native UIs interact with the shared logic via platform-specific adapters.

<div align="center">
  <img src="/sampleImages/kmp.png" width="350px" height="180px" />
</div>

---

## 🛠️ Prerequisites

Before setting up the project, ensure you have the following:

1. **Kotlin 1.9+** with the KMM plugin installed.
2. **Android Studio Giraffe** or later for Android development.
3. **Xcode 14.0+** for iOS development.
4. **Gradle 8.0+** for project builds.

---

## 📂 Project Structure

```plaintext
AniVerse/
├── androidApp/                            # Android application module
│   └── ...
├── iosApp/                                # iOS application module
│   └── ...
├── shared/                                # Shared KMM module
│   ├── commonMain/                        # Shared business logic for both platforms
│   │   └── kotlin/
│   │       └── com/
│   │           └── generativeai/aniverse/
│   │               ├── data/              # Data layer
│   │               │   ├── di/            # Dependency injection for data layer
│   │               │   ├── dto/           # Data Transfer Objects
│   │               │   ├── mappers/       # Data mapping between layers
│   │               │   ├── remote/        # API communication
│   │               │   └── repositoryImpl/# Repository implementations
│   │               ├── domain/            # Domain layer
│   │               │   ├── constants/     # Constant values
│   │               │   ├── di/            # Dependency injection for domain layer
│   │               │   ├── model/         # Domain models
│   │               │   ├── repository/    # Repository interfaces
│   │               │   ├── usecases/      # Business use cases
│   │               │   └── utils/         # Utility functions
│   │               └── presentation/      # Presentation layer
│   │                   ├── di/            # Dependency injection for presentation
│   │                   ├── intent/        # User intents
│   │                   ├── stateHandler/  # Custom state handler
│   │                   ├── uiState/       # UI State models
│   │                   └── viewModel/     # ViewModels for state management
│   └── ...
```

---

## 🚀 Getting Started

### Clone the Repository
To get started, clone the AniVerse repository:

```
git clone https://github.com/magneto3572/AniVerse.git
```

### Running on Android
1. Open the project in **Android Studio**.
2. Build the project using the `Build` menu or with Gradle.
3. Run the app on an emulator or a physical Android device.

### Running on iOS
1. Open the `iosApp` folder in **Xcode**.
2. Configure the target scheme and select a simulator or a physical iOS device.
3. Build and run the app.

---

<div align="center">
  <video src="https://github.com/user-attachments/assets/379c9cc1-99bb-4edd-8298-53726f9368d7" height="300px" controls></video>
  <video src="https://github.com/user-attachments/assets/d48ca95e-558f-437e-9470-ce3322f4ea8a" height="300px" controls></video>
</div>

## 📧 Contact

For queries, suggestions, or feedback, feel free to reach out at:
- **Email**: consult@logoredefined.com  
- **Email**: bishal.singh.3572@gmail.com  
