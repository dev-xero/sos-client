package group.one.sos.presentation.screens.emergency_contacts

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import group.one.sos.R
import group.one.sos.presentation.components.OnboardingTopBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContactsScreen(
    modifier: Modifier = Modifier,
    navigator: EmergencyContactsNavigator
) {
    Scaffold(
//        topBar = { OnboardingTopBar { navigator.navigateBack() }}
    ) { innerPadding ->
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
            }
        }
    }
}