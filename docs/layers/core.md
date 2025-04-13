# `Core Layer`

The `core/` module contains low-level utilities and abstractions shared across other layers like domain and presentation but remain independent of any specific feature or business logic.

## `Purpose`

- Centralize **common utilities** used throughout the app.
- Maintain **framework-agnostic code** where possible.
- Ensure **minimal dependency** on Android SDK or Compose.

## `Contents`

| Module        | Description                                              |
|---------------|----------------------------------------------------------|
|  `di/`        | Hilt dependency injection modules                        |
| `utils/`      | General purpose helper functions (e.g date, validation)  |
| `contants/`   | App-wide constants                                       |
| `extensions/` | Kotlin extension functions                               |
| `exceptions/` | Custom exceptions (e.g HTTP)                             |


## `Guidelines`

- Keep this layer **light weight and generic**
- Do not include framework or android specific implementations.