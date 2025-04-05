package group.one.sos.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
class PreferenceManager @Inject constructor(private val context: Context) {
    // Returns the onboarding state stored in preferences
    fun isOnboardingCompleted(): Flow<Boolean> {
        val isCompleted =
            context.appDataStore.data.map { prefs -> prefs[PreferenceKeys.ONBOARDING_KEY] ?: false }
        return isCompleted
    }

    // Completes onboarding by setting state to true
    suspend fun setOnboardingCompleted() {
        context.appDataStore.edit { prefs -> prefs[PreferenceKeys.ONBOARDING_KEY] = true }
    }
}