package group.one.sos.domain.contracts

import group.one.sos.domain.models.EmergencyServicesApiResponse

sealed class EmergencyType(val name: String) {
    data object Police : EmergencyType(name = "policeStations")
    data object Medical : EmergencyType(name = "hospitals")
    data object Fire : EmergencyType(name = "fireStations")
}

interface EmergencyApiService {
    suspend fun getEmergencyServices(type: EmergencyType = EmergencyType.Police): Result<List<EmergencyServicesApiResponse>>
}