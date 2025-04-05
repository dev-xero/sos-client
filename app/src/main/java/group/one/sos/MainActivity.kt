package group.one.sos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import group.one.sos.data.local.preferences.PreferenceManager
import group.one.sos.domain.usecases.OnboardingUseCase
import group.one.sos.presentation.theme.SOSTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var onboardingUseCase: OnboardingUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize preference manager and onboarding use case
        onboardingUseCase = OnboardingUseCase(PreferenceManager(applicationContext))
        val isOnboardingCompleted = onboardingUseCase.isOnboardingCompleted()

        setContent {
            SOSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "SOS",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SOSTheme {
        Greeting("Android")
    }
}