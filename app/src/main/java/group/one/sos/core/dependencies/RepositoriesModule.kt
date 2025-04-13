package group.one.sos.core.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.repositories.ContactsRepositoryImpl
import group.one.sos.domain.contracts.ContactsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providesContactsRepository(@ApplicationContext context: Context): ContactsRepository {
        return ContactsRepositoryImpl(context)
    }

}