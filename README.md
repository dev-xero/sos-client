
<p align="center">
  <image src="https://github.com/user-attachments/assets/d56c8eaa-ab3d-40bf-9e78-f20c77fd8a57"/>
</p>


![Kotlin](https://img.shields.io/badge/Kotlin-1.9-purple?logo=kotlin&style=for-the-badge&colorA=131820&colorB=FFFFFF)
![](https://img.shields.io/badge/Jetpack%20Compose-UI-blue?style=for-the-badge&colorA=131820&colorB=FFFFFF&logo=android)
![](https://img.shields.io/github/license/dev-xero/sos-client?style=for-the-badge&colorA=131820&colorB=FFFFFF&logo=github)

The SOS client is a Kotlin-based Android app for an **Emergency Response System (ERS)**, that quickly alerts emergency services with a single tap. It shares the user’s real-time location and notifies configured contacts instantly.

Written with Jetpack Compose, and integrates with our Spring Boot [backend](https://github.com/Anuolu-2020/sos-backend). This source code is part of the CSC 211 (Software Workshop II) course project for group 1.

## Features

### 1. SOS Button

- Single-tap button that notifies pre-configured emergency responders
- Shares real-time GPS coordinates
- Default emergency actions include:
  - Police
  - Hospital
  - Fire Station
  - Family
 
### 2. Live Location Sharing

- Real-time GPS sharing with the backend
- Enables location-aware responder dispatchng

### 3. Emergency Contact List

- Add trusted family/friends as emergency contacts
- Default contact is prompted during setup

### 4. Incident Reporting

- Report incidents like theft, accidents or fires
- Include:
  - Type of Incident
  - Description
  - Optional photo/video
  - Auto-detected and editable location

### 5. Automated Emergency Messaging

- Customizable emergency settings:
  - Auto-call / Auto-SMS
  - Silent mode
  - Who to notify and how

## Setup Instructions

### Prerequisites

- Android Studio Giraffe or later
- Kotlin 1.9+
- Android SDK 33+

### Clone the repository

```bash
git clone https://github.com/dev-xero/sos-client.git
cd sos-client
```

### Run

1. Open the project in Anrdoid Studio
2. Connect your Anroid device or emulator
3. Click Run or F10

## Libraries

- Kotlin & Jetpack Compose
- Core androidx libraries
- Splash API
- Dagger Hilt for dependency injection
- Compose Navigation
- Datastore preferences for app preferences

## Technical Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| Mobile    | Kotlin, Jetpack Compose, AndroidX   |
| Backend   | Spring Boot, PostgreSQL             |
| CI/CD     | GitHub Actions                      |
| Infra     | Docker, AWS                         |

## Contributors

- [Elochukwu Okafor (Team Lead)](https://github.com/dev-xero)
