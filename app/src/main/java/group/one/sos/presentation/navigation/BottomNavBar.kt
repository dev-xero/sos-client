package group.one.sos.presentation.navigation

import group.one.sos.R

data class BottomNavItem(
    val label: String,
    val unselectedIconRes: Int,
    val selectedIconRes: Int,
    val route: String
)

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            unselectedIconRes = R.drawable.ic_nav_unfilled_home,
            selectedIconRes = R.drawable.ic_nav_filled_home,
            route = NavigationRoute.Home.route
        ),
        BottomNavItem(
            label = "Contacts",
            unselectedIconRes = R.drawable.ic_nav_unfilled_contacts,
            selectedIconRes = R.drawable.ic_nav_filled_contacts,
            route = NavigationRoute.Contacts.route
        ),
        BottomNavItem(
            label = "Reports",
            unselectedIconRes = R.drawable.ic_nav_unfilled_report,
            selectedIconRes = R.drawable.ic_nav_filled_report,
            route = NavigationRoute.Reports.route
        ),
        BottomNavItem(
            label = "Settings",
            unselectedIconRes = R.drawable.ic_nav_unfilled_settings,
            selectedIconRes = R.drawable.ic_nav_filled_settings,
            route = NavigationRoute.Settings.route
        )
    )
}