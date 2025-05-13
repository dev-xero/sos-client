package group.one.sos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyResponse(
    val id: Int,
    val name: String,
    val type: String,
    val callcode: String,
)