package group.one.sos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import group.one.sos.core.navigation.NavigationGraph
import group.one.sos.core.utils.Tag
import group.one.sos.domain.usecases.OnboardingUseCase
import group.one.sos.presentation.theme.SOSTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var onboardingUseCase: OnboardingUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SOSTheme {
                val navController = rememberNavController()
                val onboardingStateFlow = onboardingUseCase.isOnboardingCompleted()
                val isOnboardingCompleted by onboardingStateFlow.collectAsState(initial = false)

                Log.i(Tag.Onboarding.name, "onboarding state: $isOnboardingCompleted")

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        isOnboardingCompleted = isOnboardingCompleted,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
