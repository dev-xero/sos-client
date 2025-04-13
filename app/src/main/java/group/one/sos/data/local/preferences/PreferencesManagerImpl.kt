package group.one.sos.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import group.one.sos.core.extensions.appDataStore
import group.one.sos.domain.contracts.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Preference Manager
 *
 * Responsible for managing app preferences, including onboarding, default emergency actions,
 * and contacts.
 */
@Singleton
class PreferencesManagerImpl @Inject constructor(private val context: Context) : PreferencesManager {
    override fun isOnboardingCompleted(): Flow<Boolean> {
        val isCompleted =
            context.appDataStore.data.map { prefs -> prefs[PreferenceKeys.IS_ONBOARDING_COMPLETED] == true }
        return isCompleted
    }

    override suspend fun completeOnboarding() {
        context.appDataStore.edit { prefs -> prefs[PreferenceKeys.IS_ONBOARDING_COMPLETED] = true }
    }

    override fun isLocationPermissionGranted(): Flow<Boolean> {
        val isGranted =
            context.appDataStore.data.map { prefs -> prefs[PreferenceKeys.IS_LOCATION_PERMISSION_GRANTED] == true }
        return isGranted
    }

    override suspend fun grantLocationPermission() {
        context.appDataStore.edit { prefs ->
            prefs[PreferenceKeys.IS_LOCATION_PERMISSION_GRANTED] = true
        }
    }

    override suspend fun revokeLocationPermission() {
        context.appDataStore.edit { prefs ->
            prefs[PreferenceKeys.IS_LOCATION_PERMISSION_GRANTED] = false
        }
    }
}