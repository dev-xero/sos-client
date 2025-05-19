package group.one.sos.presentation.screens.reports

import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import group.one.sos.core.constants.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

sealed class UiState {
    object Fetching : UiState()
    object Base : UiState()
}

class ReportsViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {
    private val _locationFlow = MutableStateFlow<Location?>(null)

    private var hasSetBaseState = false;

    private var locationCallBack: LocationCallback? = null

    private val _uiState = MutableStateFlow<UiState>(UiState.Fetching)
    val uiState: StateFlow<UiState> = _uiState

    init {
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000L
        ).build()

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    if (!hasSetBaseState) {
                        _uiState.value = UiState.Base
                        hasSetBaseState = true
                    }
                    _locationFlow.value = location
                    Log.d(Tag.Reports.name, "Lat: ${location.latitude}, Long: ${location.longitude}")
                }
            }
        }

        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallBack!!,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Log.e(Tag.Reports.name, "Missing location permission", e)
        }
    }

    private fun getRecentIncidents() {

    }
}