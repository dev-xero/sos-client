package group.one.sos

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import group.one.sos.core.navigation.NavigationGraph
import group.one.sos.core.navigation.NavigationRoute
import group.one.sos.core.utils.Tag
import group.one.sos.data.local.preferences.PreferenceKeys
import group.one.sos.data.local.preferences.appDataStore
import group.one.sos.domain.usecases.OnboardingUseCase
import group.one.sos.presentation.theme.SOSTheme
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SOSTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                var isReady by remember { mutableStateOf(false) }

                val isLocationPermissionGrantedFlow = remember {
                    context.appDataStore.data.map { prefs ->
                        prefs[PreferenceKeys.LOCATION_PERMISSION_GRANTED_KEY]
                    }
                }
                val isLocationPermissionGranted by isLocationPermissionGrantedFlow.collectAsState(
                    initial = null
                )

                val permissionState =
                    rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
                val currentPermissionStatus = permissionState.status

                LaunchedEffect(isLocationPermissionGranted) {
                    if (isLocationPermissionGranted != null) {
                        isReady = true
                        Log.d(
                            Tag.LocationService.name,
                            "App is ready. Permission granted in DS: $isLocationPermissionGranted, actual status: $currentPermissionStatus"
                        )
                    }
                }

                splashScreen.setKeepOnScreenCondition { !isReady }
                if (isReady) {
                    val finalStartDestination =
                        remember(currentPermissionStatus, isLocationPermissionGranted) {
                            val hasGrantedInDataStore = isLocationPermissionGranted == true
                            when {
                                currentPermissionStatus.isGranted -> NavigationRoute.Home
                                hasGrantedInDataStore -> NavigationRoute.LocationPermission
                                else -> NavigationRoute.OnboardingBegin
                            }
                        }
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavigationGraph(
                            navController = navController,
                            startDestination = finalStartDestination,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize())
                    Log.d(
                        Tag.LocationService.name,
                        "App not ready yet. Displaying splash. Permission Granted in DS: $isLocationPermissionGranted"
                    )
                }
            }
        }
    }
}
