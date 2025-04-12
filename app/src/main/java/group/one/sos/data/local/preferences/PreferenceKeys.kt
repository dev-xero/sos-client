package group.one.sos.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferenceKeys {
    val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
    val LOCATION_PERMISSION_GRANTED_KEY = booleanPreferencesKey("location_permission_granted")
    val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
}