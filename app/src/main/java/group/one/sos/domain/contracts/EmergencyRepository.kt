package group.one.sos.domain.contracts

import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import group.one.sos.domain.models.IncidentResponse
import group.one.sos.domain.models.IncidentType
import java.io.File

interface EmergencyRepository {
    suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int, // in meters
        lat: Double,
        long: Double
    ): Result<List<EmergencyResponse>>

    suspend fun reportIncident(
        incidentType: IncidentType,
        description: String,
        photos: List<File>,
        lat: Double,
        long: Double,

    ): Result<IncidentResponse>

    suspend fun getIncidents(
        lat: Double,
        long: Double,
        radius: Int
    ): Result<List<IncidentResponse>>
}