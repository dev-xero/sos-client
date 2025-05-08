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
import group.one.sos.presentation.theme.PaleMaroon

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier, navController: NavController,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(containerColor = if (isSystemInDarkTheme()) PaleMaroon else Cherry) {
        BottomNavItems.forEach { navItem ->
            val isSelected = currentRoute == navItem.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(navItem.route) },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (isSelected) navItem.selectedIconRes
                            else navItem.unselectedIconRes
                        ),
                        contentDescription = navItem.label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4F)
                )
            )
        }
    }
}