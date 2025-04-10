package group.one.sos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.domain.usecases.LocationPermissionUseCase
import javax.inject.Inject

@HiltViewModel
class LocationServiceViewModel @Inject constructor(
    private val locationPermissionUseCase: LocationPermissionUseCase
) : ViewModel() {
    suspend fun grantLocationPermission() {
        locationPermissionUseCase.grantLocationPermission()
    }

    suspend fun revokeLocationPermission() {
        locationPermissionUseCase.revokeLocationPermission()
    }
}