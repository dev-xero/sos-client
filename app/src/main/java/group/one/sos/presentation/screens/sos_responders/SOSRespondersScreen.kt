package group.one.sos.presentation.screens.sos_responders

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.presentation.screens.home.HomeViewModel

@Composable
fun SOSRespondersScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: HomeViewModel) {
    Scaffold(
        topBar = {}
    ) { innerPadding ->
        val services by viewModel.services.collectAsState()
        Log.d("HELP", services.toString())

        LazyColumn(modifier = modifier.padding(innerPadding)) {
            items(services) { service ->
                EmergencyServicePill(service = service)
            }
        }
    }
}

@Composable
private fun SOSRespondersTopBar(modifier: Modifier = Modifier) {

}

@Composable
private fun EmergencyServicePill(modifier: Modifier = Modifier, service: EmergencyResponse) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(
            text = service.name
        )
    }
}