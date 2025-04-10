package group.one.sos.core.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group.one.sos.core.utils.Tag
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
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        }
    ) {
        composable(route = NavigationRoute.OnboardingBegin.route) {
            OnboardingBeginScreen(onGetStartedClicked = {
                navController.navigate(NavigationRoute.LocationPermission.route)
            })
        }
        composable(route = NavigationRoute.LocationPermission.route) {
            LocationServicesScreen(requestLocationPermission = {

            })
        }
        composable(route = NavigationRoute.Home.route) {
            HomeScreen()
        }

    }
}