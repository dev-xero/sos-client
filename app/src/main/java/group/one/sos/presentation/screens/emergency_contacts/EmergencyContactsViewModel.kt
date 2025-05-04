package group.one.sos.presentation.screens.emergency_contacts

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.core.constants.Tag
import group.one.sos.core.extensions.appDataStore
import group.one.sos.data.local.preferences.PreferenceKeys
import group.one.sos.domain.models.ContactModel
import group.one.sos.domain.usecases.EmergencyContactUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
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
    private val emergencyContactsUseCases: EmergencyContactUseCases,
) : ViewModel() {
    private val dataStore = context.appDataStore

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var _contactsList: Flow<PagingData<ContactModel>> = emptyFlow()
    val contactsList: Flow<PagingData<ContactModel>>  get() = _contactsList

    private var hasLoadedContacts = false

    /**
     * Determines the state to dispilay based on whether contacts
     * permission has been granted.
     *
     * If we do not have permission to read contacts list, we default
     * to the permissions missing screen. Otherwise we display contacts.
     */
    fun determineUiState() {
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

    /**
     * Loads contacts from contact book.
     * 
     * A viewmodel scope is launched and we attempt to load contacts 
     * from there.
     */
    fun loadContactsList() {
        if(hasLoadedContacts) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _contactsList = emergencyContactsUseCases.getPagedContacts().cachedIn(viewModelScope)
            hasLoadedContacts = true
            _uiState.value = UiState.LoadedContactsList
        }
    }

    /**
     * Triggered when contacts permission is granted.
     *
     * Updates the permission preference on disk to prevent calling it
     * again.
     */
    fun onPermissionGranted() {
        viewModelScope.launch {
            dataStore.edit { store ->
                store[PreferenceKeys.IS_CONTACTS_PERMISSION_GRANTED] = true
            }
            determineUiState()
        }
    }

}