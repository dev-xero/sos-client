package group.one.sos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group.one.sos.presentation.screens.HomeScreen
import group.one.sos.presentation.screens.LocationServicesScreen
import group.one.sos.presentation.screens.OnboardingBeginScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isOnboardingCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination =
            if (isOnboardingCompleted) NavigationRoute.Home.route
            else NavigationRoute.OnboardingBegin.route,
        modifier = modifier
    ) {
        composable(route = NavigationRoute.OnboardingBegin.route) {
            OnboardingBeginScreen(onGetStartedClicked = {
                navController.navigate(NavigationRoute.LocationPermission.route)
            })
        }
        composable(route = NavigationRoute.LocationPermission.route) {
            LocationServicesScreen()
        }
        composable(route = NavigationRoute.Home.route) {
            HomeScreen()
        }

    }
}