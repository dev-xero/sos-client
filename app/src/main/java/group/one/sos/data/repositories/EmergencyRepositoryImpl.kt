package group.one.sos.data.repositories

import group.one.sos.data.remote.RemoteApiService
import group.one.sos.domain.contracts.EmergencyRepository
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType

class EmergencyRepositoryImpl(
    private val apiService: RemoteApiService
): EmergencyRepository {
    override suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int,
        lat: Float,
        long: Float
    ): Result<List<EmergencyResponse>> {
        return apiService.getEmergencyServices(responder, radius, lat, long)
    }
}