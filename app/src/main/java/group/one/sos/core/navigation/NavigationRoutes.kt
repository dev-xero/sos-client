package group.one.sos.core.navigation

sealed class NavigationRoute(val route: String) {
    data object OnboardingBegin : NavigationRoute("onboarding_begin")
    data object LocationPermission: NavigationRoute("location_permission")
    data object EmergencyContacts: NavigationRoute("emergency_routes")
    data object OnboardingCompleted : NavigationRoute("onboarding_completed")
    data object Home : NavigationRoute("home")
}
