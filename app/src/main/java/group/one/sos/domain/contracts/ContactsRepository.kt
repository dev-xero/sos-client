package group.one.sos.domain.contracts

import group.one.sos.domain.models.ContactModel

interface ContactsRepository {
    suspend fun getContacts(): List<ContactModel>
}