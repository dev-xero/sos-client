package group.one.sos.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class IncidentType {
    @SerialName("fire")
    FIRE,

    @SerialName("health")
    HEALTH,

    @SerialName("security")
    SECURITY,

    @SerialName("others")
    OTHERS,
}