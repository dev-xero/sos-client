package group.one.sos.domain.usecases

import group.one.sos.domain.contracts.PreferencesManager
import javax.inject.Inject

/**
 * Location Permission Use Case
 *
 * Business logic functions to granting and revoking location permissions.
 * These are stored in preferences datastore managed by the preference manager.
 */
class LocationPermissionUseCases @Inject constructor(private val preferencesManager: PreferencesManager){
    suspend fun grantLocationPermission() {
        preferencesManager.grantLocationPermission()
    }

    suspend fun revokeLocationPermission() {
        preferencesManager.revokeLocationPermission()
    }
}