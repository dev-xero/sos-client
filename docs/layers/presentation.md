# `Presentation Layer`

The `presentation/` layer handles everything related to the user interface (UI), including Composables, ViewModels, navigation and interaction logic.

## `Purpose`

- Define how information is presented to the user.
- Reactive by default and emit UI states.
- Interact with the domain layer via ViewModels.

## `Contents`

| Module        | Description                                                          |
|---------------|----------------------------------------------------------------------|
| `screens/`    | Screen-level Composable (each module with its own ui and ViewModels) |
| `components/` | Reusable UI elements                                                 |
| `navigation/` | Routes and Navigation Graphs                                         |
| `ui/`         | Themes including app colors and typography                           |

## `Guidelines`

- All files must be related to UI and not core or business logic.
- Follow the folder and naming conventions for screens and components.