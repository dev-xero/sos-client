package group.one.sos.data.repositories

import android.content.Context
import android.provider.ContactsContract
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.data.models.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getContacts(): List<ContactModel> = coroutineScope {
        async(Dispatchers.IO) { getContactList() }.await()
    }

    // Get list of contact details such as id, name, phone number &
    // photo URI from the `Phone.CONTENT_URI`
    private fun getContactList(): List<ContactModel> {
        val contactsList = mutableListOf<ContactModel>()
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )?.use { contactsCursor ->
            val idIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoURIIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
            val photoThumbIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                val number = contactsCursor.getString(numberIndex)
                val photoURI = contactsCursor.getString(photoURIIndex)
                val photoThumb = contactsCursor.getString(photoThumbIndex)

                contactsList.add(
                    ContactModel(
                        id = id,
                        displayName = name,
                        phoneNumber = number,
                        photoURI = photoURI,
                        photoThumbURI = photoThumb
                    )
                )
            }
        }

        return contactsList
    }
}