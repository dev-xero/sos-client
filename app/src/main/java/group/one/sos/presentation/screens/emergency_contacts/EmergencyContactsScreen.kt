package group.one.sos.presentation.screens.emergency_contacts

import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import group.one.sos.R
import group.one.sos.core.constants.Tag
import group.one.sos.core.utils.openAppSettings
import group.one.sos.domain.models.ContactModel
import group.one.sos.presentation.components.ContactPill
import group.one.sos.presentation.components.FilledButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContactsScreen(
    modifier: Modifier = Modifier,
    navigator: EmergencyContactsNavigator
) {
    val context = LocalContext.current
    val viewModel: EmergencyContactsViewModel = hiltViewModel()
    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)

    var hasRequested by remember { mutableStateOf(false) }
    var wasDeniedPermission by remember { mutableStateOf(false) }
    var rationaleText by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val contacts = viewModel.contactsList.collectAsLazyPagingItems()

    // Permission state side effects
    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                viewModel.onPermissionGranted()
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
           when (uiState)  {
               UiState.Loading -> {
                   SpinnerFragment()
               }

               UiState.PermissionMissing -> {
                   PermissionMissingFragment(
                       wasDeniedPermission = wasDeniedPermission,
                       rationaleText = rationaleText,
                       onRequestPermission = {
                           if (wasDeniedPermission) {
                               openAppSettings(context)
                           } else {
                               wasDeniedPermission = false
                               permissionState.launchPermissionRequest()
                               hasRequested = true
                           }
                       }
                   )
               }

               UiState.LoadedContactsList -> {
                   ContactsListFragment(contacts = contacts)
               }
           }
        }
    }
}

@Composable
private fun SpinnerFragment(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PermissionMissingFragment(
    wasDeniedPermission: Boolean,
    rationaleText: String,
    onRequestPermission: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        IntroFragment()
        FilledButton(
            action = onRequestPermission,
            textResource = if (wasDeniedPermission)
                R.string.enable_in_settings
            else
                R.string.request_permission
        )
        if (wasDeniedPermission) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$rationaleText ${stringResource(R.string.enable_permission_in_settings)}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ContactsListFragment(contacts: LazyPagingItems<ContactModel>) {
    Column(modifier = Modifier.fillMaxSize()) {
        IntroFragment(shouldShowIcon = false)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ){
            items(contacts.itemCount) { index ->
                val contact = contacts[index]
                contact?.let {
                    ContactPill(
                        displayName = it.displayName,
                        phoneNumber = it.phoneNumber
                    )
                }
            }
        }
    }
}

@Composable
private fun IntroFragment(
    modifier: Modifier = Modifier,
    shouldShowIcon: Boolean = true
) {
    if (shouldShowIcon) {
        Image(
            painter = painterResource(R.drawable.ic_contacts),
            contentDescription = null,
            modifier = modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
    Text(
        text = stringResource(R.string.add_emergency_contact),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = if (shouldShowIcon) TextAlign.Center else TextAlign.Start
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.add_emergency_contact_desc),
        textAlign = if (shouldShowIcon) TextAlign.Center else TextAlign.Start
    )
    Spacer(modifier = Modifier.height(24.dp))
}