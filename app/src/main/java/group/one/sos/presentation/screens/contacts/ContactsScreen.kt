package group.one.sos.presentation.screens.contacts

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import group.one.sos.R
import group.one.sos.domain.models.EmergencyType
import group.one.sos.presentation.components.BottomNavBar
import group.one.sos.presentation.navigation.NavigationRoute
import group.one.sos.presentation.theme.Cherry
import group.one.sos.presentation.theme.PaleMaroon
import group.one.sos.presentation.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ContactsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.navigateToResults.collect {
            navController.navigate(NavigationRoute.ContactsResults.route)
        }
    }

    Scaffold(
        topBar = { ContactsScreenTopBar() },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = NavigationRoute.Contacts.route
            )
        }
    ) { innerPadding ->
        Log.d("Conctacts!", uiState.toString())
        if (uiState == UiState.Loading || uiState == UiState.Fetching) {
            // Fullscreen loader
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        EmergencyResponderCard(
                            title = "Police",
                            iconRes = R.drawable.ic_kit_police
                        ) {
                            viewModel.fetchEmergencyServices(EmergencyType.Police)
                        }
                    }

                    item {
                        EmergencyResponderCard(
                            title = "Hospital",
                            iconRes = R.drawable.ic_kit_medical
                        ) {
                            viewModel.fetchEmergencyServices(EmergencyType.Medical)
                        }
                    }

                    item {
                        EmergencyResponderCard(
                            title = "Fire Station",
                            iconRes = R.drawable.ic_kit_fire
                        ) {
                            viewModel.fetchEmergencyServices(EmergencyType.Fire)
                        }
                    }

                    item {
                        EmergencyResponderCard(
                            title = "Family",
                            iconRes = R.drawable.ic_kit_family
                        ) {
                            // TODO: Not yet implemented
                        }
                    }
                }

                error?.let {
                    LaunchedEffect(it) {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        viewModel.clearError()
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactsScreenTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.contacts_header),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun EmergencyResponderCard(
    modifier: Modifier = Modifier,
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(64.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSystemInDarkTheme()) PaleMaroon else Cherry
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = title,
                tint = Primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}