package group.one.sos.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import group.one.sos.presentation.components.BottomNavBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
        }
    }
}