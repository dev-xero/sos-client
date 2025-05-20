package group.one.sos.presentation.screens.reports

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import group.one.sos.core.constants.Tag
import group.one.sos.domain.contracts.EmergencyRepository
import group.one.sos.domain.models.IncidentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Fetching : UiState()
    object Base : UiState()
}

class ReportsViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val emergencyRepository: EmergencyRepository,
) : ViewModel() {
    private val _locationFlow = MutableStateFlow<Location?>(null)

    private var hasSetBaseState = false;

    private var locationCallBack: LocationCallback? = null

    private val _reports = MutableStateFlow<List<IncidentResponse>>(emptyList())
    val reports: StateFlow<List<IncidentResponse>> = _reports

    private val _uiState = MutableStateFlow<UiState>(UiState.Fetching)
    val uiState: StateFlow<UiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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

    fun getRecentIncidents() {
        if (_locationFlow.value != null) {
            viewModelScope.launch {
                _uiState.value = UiState.Fetching
                _error.value = null

                emergencyRepository.getIncidents(
                    radius = 200_000,
                    lat = _locationFlow.value!!.latitude,
                    long = _locationFlow.value!!.longitude,
                )
                    .onSuccess { res ->
                        _reports.value = res
                        Log.i(Tag.Home.name, res.toString())
                    }
                    .onFailure { e ->
                        _error.value = e.message
                        Log.e(Tag.Home.name, e.message.toString())
                        _uiState.value = UiState.Base
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationCallBack?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }
}