package group.one.sos.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferenceKeys {
    val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
}