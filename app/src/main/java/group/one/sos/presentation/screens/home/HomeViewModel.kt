package group.one.sos.presentation.screens.home

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
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.core.constants.Tag
import group.one.sos.domain.contracts.EmergencyRepository
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Loading : UiState()
    object Fetching : UiState()
    object Base : UiState()
    object LoadedResponders : UiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val emergencyRepository: EmergencyRepository
) : ViewModel() {

    private val _locationFlow = MutableStateFlow<Location?>(null)
//    val locationFlow: StateFlow<Location?> = _locationFlow

    private var hasSetBaseState = false;

    private var locationCallBack: LocationCallback? = null

    private val _services = MutableStateFlow<List<EmergencyResponse>>(emptyList())
    val services: StateFlow<List<EmergencyResponse>> = _services

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _navigateToResults = MutableSharedFlow<Unit>()
    val navigateToResults: SharedFlow<Unit> = _navigateToResults

    init {
        startLocationUpdates()
    }

    // Fetch user coordinates periodically
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
                    Log.d(Tag.Home.name, "Lat: ${location.latitude}, Long: ${location.longitude}")
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
            Log.e(Tag.Home.name, "Missing location permission", e)
        }
    }

    fun sendSOSRequest() {
        Log.i(Tag.Home.name, "SOS Sent Out!")
        if (_locationFlow.value != null) {
            viewModelScope.launch {
                _uiState.value = UiState.Fetching
                _error.value = null

                emergencyRepository.getEmergencyServices(
                    responder = EmergencyType.Police,
                    radius = 300000,
                    lat = _locationFlow.value!!.latitude,
                    long = _locationFlow.value!!.longitude,
                )
                    .onSuccess { res ->
                        _services.value = res
                        Log.i(Tag.Home.name, res.toString())
                        _navigateToResults.emit(Unit)
                    }
                    .onFailure { e ->
                        _error.value = e.message
                        Log.e(Tag.Home.name, e.message.toString())
                        _uiState.value = UiState.Base
                    }
            }
        }
    }

    fun setUiStateToBase() {
        _uiState.value = UiState.Base
    }

    fun clearError() {
        _error.value = null
    }

    override fun onCleared() {
        super.onCleared()
        locationCallBack?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }
}