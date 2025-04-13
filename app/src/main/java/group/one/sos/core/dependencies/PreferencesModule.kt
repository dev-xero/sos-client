package group.one.sos.core.dependencies

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.local.preferences.PreferencesManagerImpl
import group.one.sos.domain.contracts.PreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }
}