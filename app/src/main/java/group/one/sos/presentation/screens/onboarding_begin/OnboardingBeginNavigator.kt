package group.one.sos.presentation.screens.onboarding_begin

import androidx.navigation.NavHostController
import group.one.sos.presentation.navigation.NavigationRoute

class OnboardingBeginNavigator(private val navController: NavHostController) {
    fun navigateToLocationPermission() {
        navController.navigate(NavigationRoute.LocationPermission.route)
    }
}