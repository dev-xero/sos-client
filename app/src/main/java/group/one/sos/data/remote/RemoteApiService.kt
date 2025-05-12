package group.one.sos.data.remote

import group.one.sos.domain.contracts.EmergencyApiService

class RemoteApiService: EmergencyApiService {
    private val httpClient = HttpClient
}