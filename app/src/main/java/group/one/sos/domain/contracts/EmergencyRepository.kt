package group.one.sos.domain.contracts

import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType

interface EmergencyRepository {
    suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int, // in meters
        lat: Double,
        long: Double
    ): Result<List<EmergencyResponse>>
}