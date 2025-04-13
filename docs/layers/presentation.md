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
| `ui/`         | Themes, typography, and app colors                                   |