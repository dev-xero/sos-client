package group.one.sos.presentation.screens.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import group.one.sos.presentation.components.BottomNavBar

@Composable
fun ContactsScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
        }
    }
}
