package group.one.sos.domain.models

data class EmergencyServicesApiResponse(
    val id: Int,
    val name: String,
    val type: String,
    val callcode: String,
)
