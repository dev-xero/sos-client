package group.one.sos.presentation.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import group.one.sos.R
import group.one.sos.presentation.ui.FilledButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationServicesScreen(
    modifier: Modifier = Modifier,
    requestLocationPermission: () -> Unit
) {
    val context = LocalContext.current
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var cancellationTokenSource by remember {
        mutableStateOf<CancellationTokenSource?>(null)
    }

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_location_marker),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.location_services),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.location_services_desc),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(24.dp))
                FilledButton(
                    action = requestLocationPermission,
                    textResource = R.string.request_location_permission
                )
            }
        }
    }
}