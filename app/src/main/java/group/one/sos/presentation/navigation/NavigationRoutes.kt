package group.one.sos.presentation.navigation

sealed class NavigationRoute(val route: String) {
    data object OnboardingBegin : NavigationRoute("onboarding_begin")
    data object LocationPermission: NavigationRoute("location_permission")
    data object EmergencyContacts: NavigationRoute("emergency_routes")
    data object OnboardingComplete : NavigationRoute("onboarding_complete")
    data object Home : NavigationRoute("home")
}
