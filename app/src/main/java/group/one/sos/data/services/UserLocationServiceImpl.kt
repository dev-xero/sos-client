package group.one.sos.data.services

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import group.one.sos.domain.contracts.UserLocationService

class UserLocationServiceImpl(
    private val fusedLocationClient: FusedLocationProviderClient
) : UserLocationService {
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(
        cancellationToken: CancellationTokenSource,
        onSuccess: (Double, Double) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token)
            .addOnSuccessListener { location ->
                if (location != null) onSuccess(location.latitude, location.longitude)
                else onFailure(Exception("Location is null"))
            }
            .addOnFailureListener { ex -> onFailure(ex) }
    }
}