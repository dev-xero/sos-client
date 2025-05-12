package group.one.sos.data.remote

import group.one.sos.domain.contracts.EmergencyApiService
import io.ktor.client.HttpClient

class RemoteApiService: EmergencyApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {

        }
    }
}