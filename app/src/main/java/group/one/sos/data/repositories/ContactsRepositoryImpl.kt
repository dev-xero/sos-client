package group.one.sos.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import group.one.sos.domain.contracts.ContactsRepository
import group.one.sos.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ContactsRepository {
    override fun getPagedContacts(): Flow<PagingData<ContactModel>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { ContactsPagingSource(context) }
        ).flow
    }
}