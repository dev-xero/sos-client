package group.one.sos.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
    val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
    val LOCATION_PERMISSION_GRANTED_KEY = booleanPreferencesKey("location_permission_granted")
    val EMERGENCY_CONTACT_KEY = stringPreferencesKey("emergency_contact")
}