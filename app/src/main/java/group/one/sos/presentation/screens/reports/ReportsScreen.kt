package group.one.sos.presentation.screens.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute

@Composable
fun ReportsScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Reports.route
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
        }
    }
}
