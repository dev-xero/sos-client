package group.one.sos.presentation.screens.onboarding_begin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import group.one.sos.R
import group.one.sos.presentation.components.FilledIconButton

@Composable
fun OnboardingBeginScreen(
    modifier: Modifier = Modifier,
    navigator: OnboardingBeginNavigator
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = stringResource(R.string.onboarding_text))
                Spacer(modifier = Modifier.height(24.dp))
                FilledIconButton(
                    action = { navigator.navigateToLocationPermission() },
                    iconResource = R.drawable.ic_right_arrow,
                    textResource = R.string.get_started_btn
                )
            }
        }
    }
}