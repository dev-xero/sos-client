package group.one.sos.data.repositories

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.domain.contracts.ContactsRepository
import group.one.sos.domain.models.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ContactsRepository {
    override suspend fun getContacts(): List<ContactModel> = coroutineScope {
        async(Dispatchers.IO) { getContactList() }.await()
    }

    // Get list of contact details such as id, name, phone number &
    // photo URI from the `Phone.CONTENT_URI`
    private fun getContactList(): List<ContactModel> {
        val contactsList = mutableListOf<ContactModel>()
        val phoneNumberSet = HashSet<String>()

        context.contentResolver?.query(
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

            if (idIndex == -1 || nameIndex == -1 || numberIndex == -1) {
                Log.e("ContactsRepository", "Missing required columns in contactsCursor")
                return emptyList()
            }

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                val number = contactsCursor.getString(numberIndex)
                val photoURI =
                    if (photoURIIndex != -1) contactsCursor.getString(photoURIIndex) else null
                val photoThumb =
                    if (photoThumbIndex != -1) contactsCursor.getString(photoThumbIndex) else null

                // The resolver may return duplicate contacts, so we maintain a set to prevent it
                val normalizedNumber = normalizePhoneNumber(number)
                if (normalizedNumber.isNotEmpty() && !phoneNumberSet.contains(normalizedNumber)) {
                    phoneNumberSet.add(normalizedNumber)
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
        }

        return contactsList
    }
}

// Strip out non-digit parts from the phone number
private fun normalizePhoneNumber(number: String): String {
    return number.replace(Regex("[^\\d+]"), "")
}