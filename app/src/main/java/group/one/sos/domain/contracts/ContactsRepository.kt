package group.one.sos.domain.contracts

import androidx.paging.PagingData
import group.one.sos.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getPagedContacts(): Flow<PagingData<ContactModel>>
}