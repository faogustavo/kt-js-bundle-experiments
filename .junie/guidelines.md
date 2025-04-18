# Development Guidelines for Kotlin JS Bundle Experiments

This document provides guidelines and instructions for developing and maintaining this Kotlin Multiplatform project that targets Android, iOS, JS, and Server.

## Build/Configuration Instructions

### Project Structure

The project is organized into several modules:

- `/shared`: Code shared between all client targets (Android, iOS, JS)
- `/composeApp`: Shared UI code for Android and iOS using Compose Multiplatform
- `/iosApp`: iOS application
- `/js-app`: React + Next.js web application
- `/server`: Ktor server application

### Environment Setup

1. **JDK**: The project uses Java 17 for Android compatibility. Make sure you have JDK 17 or higher installed.
2. **Gradle**: The project uses Gradle as the build system. Use the included Gradle wrapper (`./gradlew`) for all Gradle commands.
3. **Node.js and npm**: Required for the JS application. Install node accordingly to the .nvmrc file.
4. **Xcode**: Required for iOS development.
5. **Android Studio**: Recommended for Android development.

### Tool Versions Management

The project includes a `.tool-versions` file at the root directory that specifies the exact versions of tools required:

```
nodejs 20.9.0
java openjdk-17.0.2
```

This file is used by version managers like [asdf](https://asdf-vm.com/) to automatically set up the correct tool versions when working on this project. Using these exact versions ensures consistency across development environments.

To use this file with asdf:

1. Install asdf following the [official instructions](https://asdf-vm.com/guide/getting-started.html)
2. Install the required plugins:
   ```bash
   asdf plugin add nodejs
   asdf plugin add java
   ```
3. Run the following command in the project root to install and use the specified versions:
   ```bash
   asdf install
   ```

After this, the correct versions of Node.js and Java will be automatically used when working in this project directory.

### Building the Project

#### Shared Module

The shared module is the core of the project and contains code that is shared across all platforms:

```bash
./gradlew :shared:build
```

#### Android Application

```bash
./gradlew :composeApp:assembleDebug
```

#### iOS Application

Open the Xcode project and build from there:

```bash
open iosApp/iosApp.xcodeproj
```

#### JS Application

The JS application requires a two-step process:

1. Install npm dependencies:
```bash
cd js-app
npm install
```

2. Start the development servers:
```bash
npm run dev:kt  # Starts Kotlin Build Server
npm run dev     # Runs Next/React Server
```

#### Server Application

```bash
./gradlew :server:run
```

### Configuration Properties

Important configuration properties are set in `gradle.properties`:

- `kotlin.code.style=official`: Uses the official Kotlin code style
- `kotlin.js.yarn=false`: Uses npm instead of Yarn for JS dependencies
- `kotlin.js.ir.output.granularity=whole-program`: Configures JS IR output
- `io.ktor.development=true`: Enables development mode for Ktor

## Testing Information

### Test Structure

Tests in this project follow the Kotlin Multiplatform convention:

- `commonTest`: Tests that run on all platforms
- `androidTest`: Android-specific tests
- `iosTest`: iOS-specific tests
- `jsTest`: JavaScript-specific tests
- `jvmTest`: JVM-specific tests (for server)

### Setting Up Tests

To add tests to a module, you need to:

1. Add the Kotlin test dependency to the module's build.gradle.kts file:

```kotlin
sourceSets {
    commonTest.dependencies {
        implementation(kotlin("test"))
    }
    // Platform-specific test dependencies if needed
}
```

2. Create the appropriate test directory structure:

```
src/
  commonTest/
    kotlin/
      your/package/structure/
        YourTest.kt
  androidTest/
    kotlin/
      your/package/structure/
        AndroidSpecificTest.kt
  // Other platform-specific test directories
```

### Running Tests

To run tests for all platforms:

```bash
./gradlew allTests
```

To run tests for a specific module:

```bash
./gradlew :shared:allTests
```

To run tests for a specific platform:

```bash
./gradlew :shared:jvmTest
./gradlew :shared:jsTest
./gradlew :shared:iosSimulatorArm64Test  # For iOS simulator on Apple Silicon
./gradlew :shared:iosX64Test             # For iOS simulator on Intel
```

### Example Test

Here's an example of a simple test for the `Greeting` class:

```kotlin
// Note: In your actual code, you would need these imports:
// import kotlin.test.Test
// import kotlin.test.assertTrue

class GreetingTest {
    @Test
    fun testGreeting() {
        val subject = Greeting().greet()

       assertTrue(subject.startsWith("Hello,"), "Greeting should start with 'Hello,'")
       assertTrue(subject.endsWith("!"), "Greeting should end with '!'")
    }
}
```

## Additional Development Information

### Code Style

The project uses the official Kotlin code style. 
It's recommended to use the IDE's built-in formatter with the Kotlin style guide for kotlin files.
It's recommended to use ESLint and Prettier for JavaScript/TypeScript files. You can find the configuration files in the `js-app` directory.

### JavaScript-Specific Information

- The project uses Kotlin/JS IR compiler with ES modules
- TypeScript definitions are generated automatically
- The JS module name is "kt-js-experiment"
- The JS target is ES2015
- The JS module is imported in the 'js-app' react application
- The 'js-app' application uses Next.js for server-side rendering and routing

### Multiplatform Considerations

- Use `expect`/`actual` declarations for platform-specific code
- The `Platform` interface is an example of this pattern, with platform-specific implementations
- When adding new platform-specific functionality, follow this pattern

### Debugging

- For Android: Use Android Studio's debugger
- For iOS: Use Xcode's debugger
- For JS: Use browser developer tools
- For server: Use IntelliJ IDEA's debugger

### Common Issues and Solutions

1. **JS Build Issues**: If you encounter JS build issues, try:
   - Clearing the Kotlin/JS cache: `rm -rf kotlin-js-store/`
   - Rebuilding the JS module: `./gradlew :shared:jsJar`

2. **iOS Build Issues**: If Xcode can't find Kotlin modules:
   - Rebuild the shared module: `./gradlew :shared:podPublishDebugXCFramework`
   - Run `pod install` in the iOS project directory

3. **Android Build Issues**: If Android Studio can't resolve dependencies:
   - Sync the Gradle project
   - Invalidate caches and restart
