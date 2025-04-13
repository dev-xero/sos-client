package group.one.sos.domain.contracts

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun completeOnboarding()
    fun isLocationPermissionGranted(): Flow<Boolean>
    suspend fun grantLocationPermission()
    suspend fun revokeLocationPermission()
}