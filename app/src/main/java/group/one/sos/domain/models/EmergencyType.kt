package group.one.sos.domain.models

sealed class EmergencyType(val name: String) {
    data object Police : EmergencyType(name = "policeStations")
    data object Medical : EmergencyType(name = "hospitals")
    data object Fire : EmergencyType(name = "fireStations")
}
