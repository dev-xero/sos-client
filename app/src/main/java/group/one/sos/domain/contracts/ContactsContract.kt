package group.one.sos.domain.contracts

import group.one.sos.data.models.ContactModel

interface ContactsContract {
    suspend fun getContacts(): List<ContactModel>
}