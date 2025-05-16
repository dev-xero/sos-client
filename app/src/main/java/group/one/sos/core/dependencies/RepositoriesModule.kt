package group.one.sos.core.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.remote.RemoteApiService
import group.one.sos.data.repositories.ContactsRepositoryImpl
import group.one.sos.data.repositories.EmergencyRepositoryImpl
import group.one.sos.domain.contracts.ContactsRepository
import group.one.sos.domain.contracts.EmergencyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun providesContactsRepository(@ApplicationContext context: Context): ContactsRepository {
        return ContactsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providesEmergencyRepository(apiService: RemoteApiService): EmergencyRepository {
        return EmergencyRepositoryImpl(apiService)
    }
}