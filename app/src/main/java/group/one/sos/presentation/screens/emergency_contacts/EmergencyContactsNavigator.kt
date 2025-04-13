package group.one.sos.presentation.screens.emergency_contacts

import androidx.navigation.NavHostController

class EmergencyContactsNavigator(private val navController: NavHostController) {
    fun navigateBack() {
        navController.popBackStack()
    }
}