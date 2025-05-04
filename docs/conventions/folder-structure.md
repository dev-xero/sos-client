# Folder Structure Guide

```
.
├── core
│   ├── constants                  # application wide constants
│   ├── dependencies               # dependencies via Hilt
│   ├── extensions                 # kotlin extension functions
│   └── utils                      # utility functions, helpers
├── data
│   ├── local                      # local/disk data
│   │   ├── database         # database schemas and models
│   │   └── preferences      # preferences data store
│   ├── remote                     
│   ├── repositories               # repository (data holder) impl
│   └── services                   # related services
├── domain
│   ├── contracts                  # contracts/interfaces
│   ├── models                     # domain models (OOP)
│   └── usecases                   # usecases for ui layer
└── presentation
    ├── components                       # shared ui components
    ├── navigation                       # navigation related
    ├── screens                          # app screens
    └── theme                            # theme related
```

## Architecture Layers

- `core`: Core, android specific code. Not particularly related to the business logic.
- `domain`: Business/app focused logic. Interfaces and use cases live here.
- `data`: All data sources (remote/local) are implemented here.
- `presentation`: UI-related code, screens, navigation, and viewmodels.