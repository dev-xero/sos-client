package group.one.sos.presentation.screens.emergency_contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.domain.models.ContactModel
import group.one.sos.domain.usecases.EmergencyContactUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    private val emergencyContactsUseCases: EmergencyContactUseCases
) : ViewModel() {
    private val _contactsList = MutableStateFlow<List<ContactModel>?>(null)
    val contactsList: StateFlow<List<ContactModel>?> = _contactsList

    fun loadContactsList() {
        viewModelScope.launch {
            _contactsList.value = emergencyContactsUseCases.getContactList()
        }
    }
}