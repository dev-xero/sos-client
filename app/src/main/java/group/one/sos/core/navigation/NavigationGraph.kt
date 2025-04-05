package group.one.sos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group.one.sos.presentation.screens.HomeScreen
import group.one.sos.presentation.screens.OnboardingBeginScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isOnboardingCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination =
            if (isOnboardingCompleted) NavigationRoute.OnboardingBegin.route
            else NavigationRoute.Home.route,
        modifier = modifier
    ) {
        composable(route = NavigationRoute.OnboardingBegin.route) {
            OnboardingBeginScreen()
        }
        composable(route = NavigationRoute.Home.route) {
            HomeScreen()
        }
    }
}