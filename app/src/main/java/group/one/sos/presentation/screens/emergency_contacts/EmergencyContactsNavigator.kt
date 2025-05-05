package group.one.sos.presentation.screens.emergency_contacts

import androidx.navigation.NavHostController
import group.one.sos.presentation.navigation.NavigationRoute

class EmergencyContactsNavigator(private val navController: NavHostController) {
    fun navigateToOnboardingComplete() {
        navController.navigate(NavigationRoute.OnboardingComplete.route) {
            navController.popBackStack()
            popUpTo(NavigationRoute.OnboardingComplete.route) {
                inclusive = true
            }
        }
    }
}