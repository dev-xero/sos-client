package group.one.sos.domain.contracts

interface LocationPermissionChecker {
    fun hasLocationPermission(): Boolean
    fun shouldShowRationale(): Boolean
    fun requestMultipleLocationPermissions()
}