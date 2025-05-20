package group.one.sos.presentation.screens.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import group.one.sos.R
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute
import group.one.sos.presentation.theme.Primary
import group.one.sos.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: ReportsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { ReportsScreenTopBar() },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Reports.route
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_nav_filled_report),
                        contentDescription = "report",
                        tint = White
                    )
                },
                text = { Text("Report") },
                containerColor = Primary
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            onRefresh = {},
            isRefreshing = uiState == UiState.Fetching
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(12.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.reports_recent_incidents),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportsScreenTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.reports_header),
            style = MaterialTheme.typography.titleMedium
        )
    }
}