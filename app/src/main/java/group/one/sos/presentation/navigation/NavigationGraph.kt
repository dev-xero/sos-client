package group.one.sos.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import group.one.sos.presentation.screens.contacts.ContactsScreen
import group.one.sos.presentation.screens.emergency_contacts.EmergencyContactsNavigator
import group.one.sos.presentation.screens.emergency_contacts.EmergencyContactsScreen
import group.one.sos.presentation.screens.home.HomeScreen
import group.one.sos.presentation.screens.home.HomeViewModel
import group.one.sos.presentation.screens.location_services.LocationServicesNavigator
import group.one.sos.presentation.screens.location_services.LocationServicesScreen
import group.one.sos.presentation.screens.onboarding_begin.OnboardingBeginNavigator
import group.one.sos.presentation.screens.onboarding_begin.OnboardingBeginScreen
import group.one.sos.presentation.screens.onboarding_complete.OnboardingCompleteNavigator
import group.one.sos.presentation.screens.onboarding_complete.OnboardingCompleteScreen
import group.one.sos.presentation.screens.reports.ReportsScreen
import group.one.sos.presentation.screens.settings.SettingsScreen
import group.one.sos.presentation.screens.sos_responders.SOSRespondersScreen

@SuppressLint("UnrememberedGetBackStackEntry")
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
            val navigator = remember { OnboardingBeginNavigator(navController) }
            OnboardingBeginScreen(navigator = navigator)
        }
        composable(route = NavigationRoute.LocationPermission.route) {
            val navigator = remember { LocationServicesNavigator(navController) }
            LocationServicesScreen(navigator = navigator)
        }
        composable(route = NavigationRoute.EmergencyContacts.route) {
            val navigator = remember { EmergencyContactsNavigator(navController) }
            EmergencyContactsScreen(navigator = navigator)
        }

        composable(route = NavigationRoute.OnboardingComplete.route) {
            val navigator = remember { OnboardingCompleteNavigator(navController) }
            OnboardingCompleteScreen(navigator = navigator)
        }

        composable(route = NavigationRoute.Home.route) { backStackEntry ->
            val viewModel: HomeViewModel = hiltViewModel(backStackEntry)
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = NavigationRoute.SOSResponders.route) {
            val parentEntry = remember {
                navController.getBackStackEntry(NavigationRoute.Home.route)
            }
            val viewModel: HomeViewModel = hiltViewModel(parentEntry)
            SOSRespondersScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = NavigationRoute.Contacts.route) {
            ContactsScreen(navController = navController)
        }

        composable(route = NavigationRoute.Reports.route) {
            ReportsScreen(navController = navController)
        }

        composable(route = NavigationRoute.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}