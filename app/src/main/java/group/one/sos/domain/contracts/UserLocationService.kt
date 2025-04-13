package group.one.sos.domain.contracts

import com.google.android.gms.tasks.CancellationTokenSource

interface UserLocationService {
    fun getCurrentLocation(
        cancellationToken: CancellationTokenSource,
        onSuccess: (Double, Double) -> Unit,
        onFailure: (Exception) -> Unit
    )
}