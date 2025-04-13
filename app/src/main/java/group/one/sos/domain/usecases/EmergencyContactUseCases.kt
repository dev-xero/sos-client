package group.one.sos.domain.usecases

import group.one.sos.data.models.ContactModel
import group.one.sos.domain.contracts.ContactsRepository
import javax.inject.Inject

class EmergencyContactUseCases @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    suspend fun getContactList(): List<ContactModel> {
        return contactsRepository.getContacts()
    }
}