package group.one.sos.domain.contracts

import group.one.sos.domain.models.EmergencyServicesApiResponse

interface EmergencyApiService {
    suspend fun getEmergencyServices(): Result<List<EmergencyServicesApiResponse>>
}