package group.one.sos

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.presentation.navigation.NavigationRoute
import group.one.sos.core.constants.Tag
import group.one.sos.data.local.preferences.PreferenceKeys
import group.one.sos.data.local.preferences.appDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {
    private val dataStore = context.appDataStore

    private val _startDestination = MutableStateFlow<NavigationRoute?>(null)
    val startDestination: StateFlow<NavigationRoute?> = _startDestination

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    @OptIn(ExperimentalPermissionsApi::class)
    fun initialize(permissionStatus: PermissionStatus) {
        viewModelScope.launch {
            val prefs = dataStore.data.first()
            val isFirstLaunch = prefs[PreferenceKeys.FIRST_LAUNCH_KEY] ?: true
            val isPermissionGrantedInDS = prefs[PreferenceKeys.LOCATION_PERMISSION_GRANTED_KEY]
            val emergencyContact = prefs[PreferenceKeys.EMERGENCY_CONTACT_KEY] ?: ""

            if (isFirstLaunch) {
                Log.d(Tag.MainActivity.name, "First launch, start destination is onboarding")
                dataStore.edit { it[PreferenceKeys.FIRST_LAUNCH_KEY] = false }
                _startDestination.value = NavigationRoute.OnboardingBegin
            } else {
                _startDestination.value = when {
                    isPermissionGrantedInDS == true && !permissionStatus.isGranted ->
                        NavigationRoute.LocationPermission

                    emergencyContact.isEmpty() ->
                        NavigationRoute.EmergencyContacts

                    isPermissionGrantedInDS == true && permissionStatus.isGranted && emergencyContact.isNotEmpty() ->
                        NavigationRoute.Home

                    else -> NavigationRoute.OnboardingBegin
                }
            }

            _isReady.value = true
        }
    }
}