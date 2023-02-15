# Fitness App project

By Itiel Olenick and Nir Zaid

## Getting started

### Prerequisites

- Backend

Docker installed on your system.

- Frontend

Android studio installed on your system, or any other Android emulator. 

- backend

  The backend consists of a database (MySQL) and a server (JAVA).

  Navigate to the root folder of the project, and run

  ```
  docker compose up --build
  ```

  The database will start up followed by the server.

  Please wait for the ready message before starting the frontend.

- Frontend

  The frontend is made of a android app (APK). Once the backend is ready,

  Launch the APK with your Android Emulator. If using Android Studio, follow these steps:
  
  Go to File > Profile or Debug APK > AndroidFitnessApp.apk (Select from where the file is located.)

  Once Android Studio finishes loading the apk file, If you have a selected AVD (Android Virtual Device) - Press the green 'Run' button at the top, and the application should run.

  if there is no selected AVD (Android Virtual Device) - select one from the scrollbar to the left of the 'Run' button.

  if the list of available AVDs is empty, navigate to the Device Manager and press 'Create Device'.

  a Virtual Device Configuration window will open. Select a phone that has the 'Play Store' field non-empty and press next.

  After selecting a device, select an Android version for the device to install. press Next and wait for the download to complete.

  After that, make sure the AVD you installed is selected. And press the 'Run' button. The application should now run.



