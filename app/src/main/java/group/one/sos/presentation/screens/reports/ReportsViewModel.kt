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
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.core.constants.Tag
import group.one.sos.domain.contracts.EmergencyRepository
import group.one.sos.domain.models.IncidentResponse
import group.one.sos.domain.models.IncidentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

sealed class UiState {
    object Fetching : UiState()
    object Base : UiState()
}


@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val emergencyRepository: EmergencyRepository,
) : ViewModel() {
    private val _locationFlow = MutableStateFlow<Location?>(null)
    private val _reports = MutableStateFlow<List<IncidentResponse>>(emptyList())
    val reports: StateFlow<List<IncidentResponse>> = _reports

    private val _uiState = MutableStateFlow<UiState>(UiState.Fetching)
    val uiState: StateFlow<UiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var hasSetBaseState = false
    private var hasFetchedIncidents = false
    private var locationCallBack: LocationCallback? = null

    init {
        startLocationUpdates()
        observeLocation()
    }

    fun getRecentIncidents() {
        viewModelScope.launch {
            val location = _locationFlow.filterNotNull().first()
            getRecentIncidents(location)
        }
    }

    fun submitReport(
        description: String,
        selectedType: IncidentType,
        addressed: Boolean,
        image: File
    ) {
        Log.d(Tag.Reports.name, "Report Incident!")
        Log.d(
            Tag.Reports.name,
            "description: $description\nselected type: ${selectedType.toString()}\naddressed: $addressed\nimage: ${image.isFile}"
        )

        viewModelScope.launch {
            val location = _locationFlow.filterNotNull().first()

            emergencyRepository.reportIncident(
                description = description,
                incidentType = selectedType,
                // We need a list of images, but presently we don't support
                // selecting multiple images
                photos = image,
                lat = location.latitude,
                long = location.longitude
            ).onSuccess {
                Log.i(Tag.Reports.name, "Incident reported successfully")
                getRecentIncidents(location)
            }.onFailure { e ->
                _error.value = "We could not report this incident"
                Log.e(Tag.Reports.name, e.message.toString())
                _uiState.value = UiState.Base
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationCallBack?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun showPhotoError() {
        _error.value = "Could not process this photo"
    }

    // Begin observing location changes
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
                    Log.d(
                        Tag.Reports.name,
                        "Lat: ${location.latitude}, Long: ${location.longitude}"
                    )
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

    private fun observeLocation() {
        viewModelScope.launch {
            _locationFlow
                .filterNotNull()
                .filter { !hasFetchedIncidents }
                .collect { location ->
                    hasFetchedIncidents = true
                    getRecentIncidents(location)
                }
        }
    }

    private suspend fun getRecentIncidents(location: Location) {
        _uiState.value = UiState.Fetching
        _error.value = null

        emergencyRepository.getIncidents(
            radius = 200_000,
            lat = location.latitude,
            long = location.longitude,
        )
            .onSuccess { res ->
                _reports.value = res
                Log.i(Tag.Reports.name, res.toString())
                _uiState.value = UiState.Base
            }
            .onFailure { e ->
                _error.value = "Something went wrong with the server"
                Log.e(Tag.Reports.name, e.message.toString())
                _uiState.value = UiState.Base
            }
    }
}
