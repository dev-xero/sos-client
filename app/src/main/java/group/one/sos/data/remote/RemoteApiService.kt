package group.one.sos.data.remote

import group.one.sos.domain.contracts.EmergencyApiService
import group.one.sos.domain.contracts.EmergencyType
import group.one.sos.domain.models.EmergencyServicesApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RemoteApiService : EmergencyApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val baseURL = "https://sosbackend-api.onrender.com/api"

    override suspend fun getEmergencyServices(responder: EmergencyType): Result<List<EmergencyServicesApiResponse>> {
        return try {
            val response: List<EmergencyServicesApiResponse> =
                httpClient.get("$baseURL/emergencyServices?type=${responder.name}").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}