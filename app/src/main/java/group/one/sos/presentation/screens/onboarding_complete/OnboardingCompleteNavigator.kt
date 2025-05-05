package group.one.sos.presentation.screens.onboarding_complete

import androidx.navigation.NavController
import group.one.sos.presentation.navigation.NavigationRoute

class OnboardingCompleteNavigator(private val navController: NavController) {
    fun navigateToHomeScreen() {
        navController.navigate(NavigationRoute.Home.route) {
            navController.popBackStack()
            popUpTo(NavigationRoute.Home.route) {
                inclusive = true
            }
        }
    }
}