package group.one.sos.core.dependencies

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.local.preferences.PreferencesManagerImpl
import group.one.sos.domain.contracts.PreferencesManager
import group.one.sos.domain.usecases.LocationPermissionUseCases
import group.one.sos.domain.usecases.OnboardingUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideOnboardingUseCase(preferencesManagerImpl: PreferencesManagerImpl): OnboardingUseCases {
        return OnboardingUseCases(preferencesManagerImpl)
    }

    @Provides
    @Singleton
    fun provideLocationPermissionUseCase(preferencesManager: PreferencesManager) : LocationPermissionUseCases {
        return LocationPermissionUseCases(preferencesManager)
    }

}