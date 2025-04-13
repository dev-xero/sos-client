package group.one.sos.presentation.screens.location_services

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.domain.usecases.LocationPermissionUseCases
import javax.inject.Inject

@HiltViewModel
class LocationServicesViewModel @Inject constructor(
    private val locationPermissionUseCases: LocationPermissionUseCases
) : ViewModel() {
    suspend fun grantLocationPermission() {
        locationPermissionUseCases.grantLocationPermission()
    }

    suspend fun revokeLocationPermission() {
        locationPermissionUseCases.revokeLocationPermission()
    }
}