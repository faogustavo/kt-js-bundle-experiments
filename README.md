# Kotlin JS Experiments Repo

This is a Kotlin Multiplatform project targeting Android, iOS, JS, and Server. It was created using the KMP Wizard
web page, and added JS dependencies manually. The idea is to experiment with a Kotlin Multiplatform project that 
contains a web, a server, and native applications.

The file structure is as follows:

* `/shared` is for the code that will be shared between all client targets in the project.
* `/composeApp` shared UI Code for Android and iOS using Compose Multiplatform;
* `/iosApp` iOS Application to run the shared UI;
* `/js-app` is a React + Next web application connecting to the shared code (No Compose);
* `/server` is for the Ktor server application, to simulate API requests;

## Running the project

### Android

Android is straightforward, just run the `ComposeApp` android application in your Android Studio or IntelliJ. 
The app will run on the emulator or connected device.

### iOS

Similar to Android, just open the `iosApp` project in Xcode and run the application. You can open the project in two
different ways:

1. Using the bash command: `open iosApp/iosApp.xcodeproj`;
2. By going to the `xcodeproj` file on Android Studio (or IntelliJ), right-clicking on it, and selecting 
   "Open in Associated Application".

### JS

JS Applications are a bit more complex, but still pretty straightforward.
First, you must install all NPM dependencies. You can do this by running the following command in the `js-app` directory:

```bash
npm install
```

After that, you can start the development server using the following two commands:

```bash
npm run dev:kt # Starts Kotlin Build Server
npm run dev # Runs Next/React Server
```
