package group.one.sos.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import group.one.sos.presentation.screens.HomeScreen
import group.one.sos.presentation.screens.LocationServicesScreen
import group.one.sos.presentation.screens.OnboardingBeginScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: NavigationRoute,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
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
            LocationServicesScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onLocationPermissionGranted = {
                    navController.navigate(NavigationRoute.Home.route) {
                        popUpTo(NavigationRoute.LocationPermission.route) {
                            inclusive = true
                        }
                    }
                })
        }
        composable(route = NavigationRoute.Home.route) {
            HomeScreen()
        }

    }
}