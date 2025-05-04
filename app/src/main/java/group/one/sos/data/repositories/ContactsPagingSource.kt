package group.one.sos.data.repositories

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.domain.models.ContactModel

class ContactsPagingSource (
    @ApplicationContext private val context: Context,
) : PagingSource<Int, ContactModel>() {

    override suspend fun load(params: LoadParams<Int>) : LoadResult<Int, ContactModel> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val allContacts = getContactList()
            val fromIndex = page * pageSize
            val toIndex = minOf(fromIndex + pageSize, allContacts.size)

            val pageData = if (fromIndex < allContacts.size) {
                allContacts.subList(fromIndex, toIndex)
            } else {
                emptyList<ContactModel>()
            }

            LoadResult.Page(
                data =  pageData,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (toIndex < allContacts.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ContactModel>): Int? {
       return state.anchorPosition?.let { anchorPos ->
           val anchorPage = state.closestPageToPosition(anchorPos)
           anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
       }
    }

    /**
     * Get a list of contact details such as id, name, phone number and photo
     * URI from the `Phone.CONTENT_URI`
     */
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

    // Strip out non-digit parts from the phone number
    private fun normalizePhoneNumber(number: String): String {
        return number.replace(Regex("[^\\d+]"), "")
    }
}