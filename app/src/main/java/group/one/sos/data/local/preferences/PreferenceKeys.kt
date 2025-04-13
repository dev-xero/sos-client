package group.one.sos.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val IS_FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    val IS_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    val IS_LOCATION_PERMISSION_GRANTED = booleanPreferencesKey("location_permission_granted")
    val IS_CONTACTS_PERMISSION_GRANTED = booleanPreferencesKey("contacts_permission_granted")
    val EMERGENCY_CONTACT = stringPreferencesKey("emergency_contact")
}