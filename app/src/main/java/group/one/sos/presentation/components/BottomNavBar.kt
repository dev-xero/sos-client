package group.one.sos.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import group.one.sos.presentation.navigation.Constants.BottomNavItems
import group.one.sos.presentation.theme.Cherry
import group.one.sos.presentation.theme.Maroon
import group.one.sos.presentation.theme.Primary

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier, navController: NavController,
    currentRoute: String
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(containerColor = if (isSystemInDarkTheme()) Maroon else Cherry.copy(0.1F)) {
        BottomNavItems.forEach { navItem ->

            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (currentRoute == navItem.route) navItem.selectedIconRes
                            else navItem.unselectedIconRes
                        ),
                        contentDescription = navItem.label,
                        tint = if (currentRoute == navItem.route) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        color = if (currentRoute == navItem.route) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Primary.copy(alpha = 0.2F)
                )
            )
        }
    }
}