package group.one.sos.presentation.screens.emergency_contacts

import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import group.one.sos.R
import group.one.sos.core.constants.Tag
import group.one.sos.core.utils.openAppSettings
import group.one.sos.presentation.components.FilledButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContactsScreen(
    modifier: Modifier = Modifier,
    navigator: EmergencyContactsNavigator
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
    var hasRequested by remember { mutableStateOf(false) }
    var wasDeniedPermission by remember { mutableStateOf(false) }
    var rationaleText by remember { mutableStateOf("") }

    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                // TODO: view model should now attempt to read contacts
            }

            hasRequested -> {
                wasDeniedPermission = true
                rationaleText =
                    if (permissionState.status.shouldShowRationale)
                        "Permission is required to read your contact list"
                    else "Contacts access is required"
                Log.d(Tag.EmergencyContact.name, rationaleText)
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
            Column {
                Image(
                    painter = painterResource(R.drawable.ic_contacts),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.add_emergency_contact),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.add_emergency_contact_desc),
                )
                Spacer(modifier = modifier.height(24.dp))
                if (!hasRequested || wasDeniedPermission) {
                    FilledButton(
                        action = {
                            if (wasDeniedPermission) {
                                openAppSettings(context)
                            } else {
                                wasDeniedPermission = false
                                permissionState.launchPermissionRequest()
                                hasRequested = true
                            }
                        },
                        textResource = if (wasDeniedPermission) R.string.enable_in_settings
                        else R.string.request_permission

                    )
                    if (wasDeniedPermission) {
                        Spacer(modifier = modifier.height(24.dp))
                        Text(
                            text = rationaleText + " " + stringResource(R.string.enable_permission_in_settings),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 18.sp
                        )
                    }
                } else {
                    // By this time the view model would've already fetched the contacts
                    // list, else we just show a spinner
                    SpinnerFragment()
                }
            }
        }
    }
}

@Composable
private fun SpinnerFragment(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
