package group.one.sos.presentation.screens.emergency_contacts

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.core.constants.Tag
import group.one.sos.core.extensions.appDataStore
import group.one.sos.data.local.preferences.PreferenceKeys
import group.one.sos.domain.models.ContactModel
import group.one.sos.domain.usecases.EmergencyContactUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Loading : UiState()
    object PermissionMissing : UiState()
    object LoadedContactsList : UiState()
}

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val emergencyContactsUseCases: EmergencyContactUseCases
) : ViewModel() {
    private val dataStore = context.appDataStore

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _contactsList = MutableStateFlow<List<ContactModel>>(emptyList())
    val contactsList: StateFlow<List<ContactModel>?> = _contactsList

    init {
        determineUIState()
    }

    fun determineUIState() {
        viewModelScope.launch {
            val store = dataStore.data.first()
            val isContactsPermissionGranted =
                store[PreferenceKeys.IS_CONTACTS_PERMISSION_GRANTED] ?: false

            Log.d(Tag.EmergencyContact.name, "is permission granted: $isContactsPermissionGranted")
            if (!isContactsPermissionGranted) {
                _uiState.value = UiState.PermissionMissing
            } else {
                loadContactsList()
            }
        }
    }

    fun loadContactsList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _contactsList.value = emergencyContactsUseCases.getContactList()
            _uiState.value = UiState.LoadedContactsList
        }
    }

    fun onPermissionGranted() {
        viewModelScope.launch {
            dataStore.edit { store ->
                store[PreferenceKeys.IS_CONTACTS_PERMISSION_GRANTED] = true
            }
            _uiState.value = UiState.Loading
            loadContactsList()
        }
    }

}