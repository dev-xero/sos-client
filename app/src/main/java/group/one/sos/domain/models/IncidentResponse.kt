package group.one.sos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class IncidentResponse(
    val id: Int,
    val typeOfIncident: IncidentType,
    val description: String,
    val isAddressed: Boolean,
    val pictures: List<Pictures>,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class Pictures(
    val id: Int,
    val url: String
)