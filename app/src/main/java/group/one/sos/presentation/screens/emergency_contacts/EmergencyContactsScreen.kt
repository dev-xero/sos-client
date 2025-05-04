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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import group.one.sos.presentation.components.FilledButton
import group.one.sos.presentation.screens.emergency_contacts.ui.ContactPill
import group.one.sos.presentation.theme.Maroon
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(
    modifier: Modifier = Modifier,
    navigator: EmergencyContactsNavigator
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModel: EmergencyContactsViewModel = hiltViewModel()

    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
    var hasRequested by remember { mutableStateOf(false) }
    var wasDeniedPermission by remember { mutableStateOf(false) }
    var rationaleText by remember { mutableStateOf("") }

    var shouldShowBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val searchTerm by viewModel.searchTerm.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val contacts = viewModel.contactsList.collectAsLazyPagingItems()
    val selectedContact = viewModel.selectedContact.collectAsState()

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
            when (uiState) {
                UiState.Loading -> {
                    SpinnerView()
                }

                UiState.PermissionMissing -> {
                    PermissionMissingView(
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
                    ContactsListView(
                        contacts = contacts,
                        viewModel = viewModel,
                        showBottomSheet = { shouldShowBottomSheet = true }
                    )
                }
            }
        }

        // Bottom sheet to select/deselect emergency contact
        if (shouldShowBottomSheet && selectedContact.value != null) {
            ModalBottomSheet(
                onDismissRequest = { shouldShowBottomSheet = false },
                sheetState = sheetState,
                containerColor = Maroon
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = stringResource(R.string.add_this_number),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedContact.value!!.displayName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedContact.value!!.phoneNumber,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FilledButton(
                        action = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    shouldShowBottomSheet = false
                                }
                            }
                        },
                        textResource = R.string.dismiss_modal,
                        secondary = true
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FilledButton(
                        action = {
                          // make emergency contact
                        },
                        textResource = R.string.yes_choose_contact
                    )
                }
            }
        }
    }
}

@Composable
private fun TopView(
    modifier: Modifier = Modifier,
    shouldShowIcon: Boolean = true,
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

@Composable
private fun SpinnerView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PermissionMissingView(
    wasDeniedPermission: Boolean,
    rationaleText: String,
    onRequestPermission: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TopView()
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
private fun ContactsListView(
    contacts: LazyPagingItems<ContactModel>,
    viewModel: EmergencyContactsViewModel,
    showBottomSheet: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopView(shouldShowIcon = false)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            // Prototype
//            item {
//                SearchBar(
//                    value = viewModel.searchTerm.collectAsState().value,
//                    onValueChange = { viewModel.updateSearchTerm(it) }
//                )
//            }
            items(contacts.itemCount) { index ->
                val contact = contacts[index]
                contact?.let {
                    ContactPill(
                        displayName = it.displayName,
                        phoneNumber = it.phoneNumber,
                        onClick = {
                            viewModel.setSelectedContact(it)
                            showBottomSheet()
                        }
                    )
                }
            }
        }
    }
}
