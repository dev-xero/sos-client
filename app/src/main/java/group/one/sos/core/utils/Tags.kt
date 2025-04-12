package group.one.sos.core.utils

sealed class Tag(val name: String) {
    data object MainActivity : Tag(name = "Main Activity")
    data object Onboarding : Tag(name = "Onboarding")
    data object Home : Tag(name = "Home")
    data object LocationService : Tag(name = "Location Service")
}