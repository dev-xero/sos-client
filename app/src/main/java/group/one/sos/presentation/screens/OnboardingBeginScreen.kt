package group.one.sos.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import group.one.sos.R
import group.one.sos.presentation.theme.SOSTheme

@Composable
fun OnboardingBeginScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Text(text = stringResource(R.string.onboarding_text))
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    SOSTheme {
        OnboardingBeginScreen()
    }
}