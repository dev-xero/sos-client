package group.one.sos.domain.usecases

import group.one.sos.data.local.preferences.PreferenceManager
import javax.inject.Inject

/**
 * Location Permission Use Case
 *
 * Business logic functions to granting and revoking location permissions.
 * These are stored in preferences datastore managed by the preference manager.
 */
class LocationPermissionUseCase @Inject constructor(private val preferenceManager: PreferenceManager){
    suspend fun grantLocationPermission() {
        preferenceManager.grantLocationPermission()
    }

    suspend fun revokeLocationPermission() {
        preferenceManager.revokeLocationPermission();
    }
}