package group.one.sos.domain.contracts

import android.content.Context
import group.one.sos.data.models.ContactModel

interface ContactsRepository {
    suspend fun getContacts(): List<ContactModel>
}