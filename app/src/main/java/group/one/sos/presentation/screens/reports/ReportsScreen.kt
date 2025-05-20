package group.one.sos.presentation.screens.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import group.one.sos.R
import group.one.sos.domain.models.IncidentResponse
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute
import group.one.sos.presentation.theme.Cherry
import group.one.sos.presentation.theme.LimeGreen
import group.one.sos.presentation.theme.PaleMaroon
import group.one.sos.presentation.theme.Primary
import group.one.sos.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(modifier: Modifier = Modifier, navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: ReportsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val error by viewModel.error.collectAsState()
    val reports by viewModel.reports.collectAsState()

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error!!)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = { ReportsScreenTopBar() },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Reports.route
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (uiState != UiState.Fetching) {
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
        }
    ) { innerPadding ->
        PullToRefreshBox(
            onRefresh = { viewModel.getRecentIncidents() },
            isRefreshing = uiState == UiState.Fetching
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.reports_recent_incidents),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Display incident reports
                items(reports) { report ->
                    IncidentCard(incident = report)
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

@Composable
private fun IncidentCard(modifier: Modifier = Modifier, incident: IncidentResponse) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSystemInDarkTheme()) PaleMaroon else Cherry
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            if (!incident.pictures[0].url.isNullOrBlank()) {
                AsyncImage(
                    model = incident.pictures[0].url,
                    contentDescription = "Incident image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                text = incident.description,
                style = MaterialTheme.typography.headlineSmall,
                color = Primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (incident.isAddressed) "Has been addressed" else "Has not been addressed",
                style = MaterialTheme.typography.bodyMedium,
                color = if (incident.isAddressed) LimeGreen else Color(0xFFF5A822)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Type: ${incident.typeOfIncident}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "Latitude: ${incident.latitude}",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Longitude: ${incident.longitude}",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}