package group.one.sos.presentation.screens.emergency_contacts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import group.one.sos.domain.usecases.EmergencyContactUseCases
import javax.inject.Inject

@HiltViewModel
class EmergencyContactsViewModel @Inject constructor(
    emergencyContactsUseCases: EmergencyContactUseCases
) : ViewModel() {

}