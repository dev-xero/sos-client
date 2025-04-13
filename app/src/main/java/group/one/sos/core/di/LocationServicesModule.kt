package group.one.sos.core.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.local.preferences.PreferenceManager
import group.one.sos.domain.usecases.LocationPermissionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationServicesModule {
    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context);
    }
    
    @Provides
    @Singleton
    fun provideLocationPermissionUseCase(preferenceManager: PreferenceManager) : LocationPermissionUseCase {
        return LocationPermissionUseCase(preferenceManager)
    }
}