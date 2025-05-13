package group.one.sos.presentation.screens.sos_responders

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import group.one.sos.R
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.presentation.screens.home.HomeViewModel
import group.one.sos.presentation.theme.Cherry
import group.one.sos.presentation.theme.PaleMaroon
import group.one.sos.presentation.theme.Primary

@Composable
fun SOSRespondersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    Scaffold(
        topBar = {
            SOSRespondersTopBar(onBackClicked = {
                viewModel.setUiStateToBase()
                navController.popBackStack()
            })
        }
    ) { innerPadding ->
        val services by viewModel.services.collectAsState()

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(services) { service ->
                EmergencyServicePill(service = service)
            }
        }
    }
}

@Composable
private fun SOSRespondersTopBar(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = null,
                tint = Primary
            )
        }

        Text(
            text = stringResource(R.string.nearest_emergency_responders),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun EmergencyServicePill(modifier: Modifier = Modifier, service: EmergencyResponse) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSystemInDarkTheme()) PaleMaroon else Cherry)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_phone_ring),
                contentDescription = null,
                tint = Primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = service.name,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.callcode.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}