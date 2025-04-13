package group.one.sos.core.constants

sealed class Tag(val name: String) {
    data object MainActivity : Tag(name = "Main Activity")
    data object Onboarding : Tag(name = "Onboarding")
    data object LocationService : Tag(name = "Location Service")
    data object EmergencyContact: Tag(name = "Emergency Contact")
    data object Home : Tag(name = "Home")
}