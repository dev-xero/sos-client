package group.one.sos.presentation.screens.home

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.core.constants.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    private val _locationFlow = MutableStateFlow<Location?>(null)
    val locationFlow: StateFlow<Location?> = _locationFlow

    private var locationCallBack: LocationCallback? = null

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

    override fun onCleared() {
        super.onCleared()
        locationCallBack?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }
}