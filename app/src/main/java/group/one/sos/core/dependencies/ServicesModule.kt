package group.one.sos.core.dependencies

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.one.sos.data.services.UserLocationServiceImpl
import group.one.sos.domain.contracts.UserLocationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideUserLocationService(fusedLocationProviderClient: FusedLocationProviderClient): UserLocationService {
        return UserLocationServiceImpl(fusedLocationProviderClient)
    }
}