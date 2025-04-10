package group.one.sos.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationService(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
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

    fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }
}