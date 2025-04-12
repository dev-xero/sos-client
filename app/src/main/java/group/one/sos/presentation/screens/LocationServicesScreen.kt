package group.one.sos.presentation.screens

import android.Manifest
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import group.one.sos.R
import group.one.sos.core.utils.Tag
import group.one.sos.presentation.ui.FilledButton
import group.one.sos.presentation.viewmodels.LocationServiceViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationServicesScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationServiceViewModel = hiltViewModel(),
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var hasRequested by remember { mutableStateOf(false) }
    var rationaleText by remember { mutableStateOf("") }
    var showDeniedMessage by remember { mutableStateOf(false) }

    // Observe whenever permission state changes
    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                viewModel.grantLocationPermission()
                showDeniedMessage = false
                Log.d(Tag.LocationService.name, "Location permission granted.")
            }

            hasRequested -> {
                showDeniedMessage = true
                rationaleText = if (permissionState.status.shouldShowRationale)
                    "Location permission is required to use core features of this app."
                else
                    "Location access is required to proceed."
                viewModel.revokeLocationPermission()
                Log.d(Tag.LocationService.name, rationaleText)
            }
        }
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
                    action = {
                        permissionState.launchPermissionRequest()
                        hasRequested = true
                    },
                    textResource = R.string.request_location_permission
                )
                if (showDeniedMessage) {
                    Spacer(modifier = modifier.height(24.dp))
                    Text(
                        text = rationaleText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}