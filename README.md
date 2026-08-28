# SheShield - Women Safety Application

SheShield is an Android-based women safety application designed to provide quick access to emergency and personal safety features.

## Features

- SOS Emergency Alert
- Voice SOS
- Power Button SOS Trigger
- Live Location
- Trusted Contacts
- Safe Zones
- Fake Call
- Emergency Services
- Safety Tips
- Incident Reporting
- Secret Evidence Recording
- User Authentication
- Multiple Safety Tools

## Technology Stack

- Android Studio
- Java
- XML
- Firebase
- Vosk Speech Recognition
- Google Maps

## Project Structure

The application contains multiple activities and services for handling different safety features.

### Important Components

- `MainActivity` - Main dashboard
- `VoiceSosService` - Voice-based SOS detection
- `ScreenOffSosService` - Power button / screen interaction SOS handling
- `TrustedContactsActivity` - Manage emergency contacts
- `LiveLocationActivity` - Share and view live location
- `SafeZonesActivity` - Safety zone management
- `FakeCallActivity` - Simulated incoming call
- `ReportIncidentActivity` - Incident reporting
- `SafetyTipsActivity` - Personal safety guidance
- `EmergencyServicesActivity` - Emergency service information

## Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Build and run the application on an Android device.

## Build APK

To generate a debug APK:

```powershell
.\gradlew.bat assembleDebug
