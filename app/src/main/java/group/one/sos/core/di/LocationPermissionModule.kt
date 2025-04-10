package group.one.sos.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.local.preferences.PreferenceManager
import group.one.sos.domain.usecases.LocationPermissionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationPermissionModule {
    @Provides
    @Singleton
    fun provideLocationPermissionUseCase(preferenceManager: PreferenceManager) : LocationPermissionUseCase {
        return LocationPermissionUseCase(preferenceManager)
    }
}