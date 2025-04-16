package group.one.sos.presentation.screens.location_services

import androidx.navigation.NavHostController
import group.one.sos.presentation.navigation.NavigationRoute

class LocationServicesNavigator(private val navController : NavHostController) {
    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToEmergencyContacts() {
        navController.navigate(NavigationRoute.EmergencyContacts.route) {
            popUpTo(NavigationRoute.EmergencyContacts.route) {
                inclusive = true
            }
        }
    }
}