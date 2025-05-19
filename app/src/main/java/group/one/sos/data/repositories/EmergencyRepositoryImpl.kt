package group.one.sos.data.repositories

import group.one.sos.data.remote.RemoteApiService
import group.one.sos.domain.contracts.EmergencyRepository
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import group.one.sos.domain.models.IncidentResponse
import group.one.sos.domain.models.IncidentType
import java.io.File
import javax.inject.Inject

class EmergencyRepositoryImpl @Inject constructor(
    private val apiService: RemoteApiService
) : EmergencyRepository {
    override suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int,
        lat: Double,
        long: Double,
    ): Result<List<EmergencyResponse>> {
        return apiService.getEmergencyServices(responder, radius, lat, long)
    }

    override suspend fun reportIncident(
        incidentType: IncidentType,
        description: String,
        photos: List<File>,
        lat: Double,
        long: Double
    ): Result<IncidentResponse> {
        return apiService.reportIncident(incidentType, description, photos, lat, long)
    }

    override suspend fun getIncidents(
        lat: Double,
        long: Double,
        radius: Int
    ): Result<List<IncidentResponse>> {
       return apiService.getIncidents(lat, long, radius)
    }
}