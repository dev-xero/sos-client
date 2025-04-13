package group.one.sos.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.repositories.ContactsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsModule {
    @Provides
    @Singleton
    fun providesContactsRepository(@ApplicationContext context: Context): ContactsRepository {
        return ContactsRepository(context)
    }
}