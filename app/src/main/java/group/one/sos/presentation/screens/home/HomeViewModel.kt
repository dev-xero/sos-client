package group.one.sos.presentation.screens.home

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val emergencyRepository: EmergencyRepository
) : ViewModel() {

    private val _locationFlow = MutableStateFlow<Location?>(null)
//    val locationFlow: StateFlow<Location?> = _locationFlow

    private var locationCallBack: LocationCallback? = null

    var services by mutableStateOf<List<EmergencyResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
                isLoading = true
                _error.value = null

                emergencyRepository.getEmergencyServices(
                    responder = EmergencyType.Police,
                    radius = 30000,
                    lat = _locationFlow.value!!.latitude,
                    long = _locationFlow.value!!.longitude,
                )
                    .onSuccess { res ->
                        services = res
                        Log.i(Tag.Home.name, res.toString())
                    }
                    .onFailure { e ->
                        _error.value = e.message
                        Log.e(Tag.Home.name, e.message.toString())
                    }

                isLoading = false
            }
        }
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