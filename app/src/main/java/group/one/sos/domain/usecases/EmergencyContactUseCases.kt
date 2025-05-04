package group.one.sos.domain.usecases

import androidx.paging.PagingData
import group.one.sos.domain.contracts.ContactsRepository
import group.one.sos.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmergencyContactUseCases @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    fun getPagedContacts(): Flow<PagingData<ContactModel>> {
        return contactsRepository.getPagedContacts()
    }
}