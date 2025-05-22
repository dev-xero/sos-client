package group.one.sos.presentation.navigation

sealed class NavigationRoute(val route: String) {
    data object OnboardingBegin : NavigationRoute("onboarding_begin")
    data object LocationPermission: NavigationRoute("location_permission")
    data object EmergencyContacts: NavigationRoute("emergency_routes")
    data object OnboardingComplete : NavigationRoute("onboarding_complete")
    data object Home : NavigationRoute("home")
    data object SOSResponders: NavigationRoute("sos_responders")
    data object Contacts : NavigationRoute("contacts")
    data object ContactsResults: NavigationRoute("contacts_result")
    data object Reports : NavigationRoute("reports")
    data object Settings : NavigationRoute("settings")
}
