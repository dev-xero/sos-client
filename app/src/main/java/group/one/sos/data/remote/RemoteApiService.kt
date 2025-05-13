package group.one.sos.data.remote

import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RemoteApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }

    private val baseURL = "https://sosbackend-api.onrender.com/api"

     suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int,
        lat: Float,
        long: Float
    ): Result<List<EmergencyResponse>> {
        return try {
            val response: List<EmergencyResponse> =
                httpClient.get("$baseURL/emergencyServices") {
                    url {
                        parameters.append("type", responder.name)
                        parameters.append("latitude", lat.toString())
                        parameters.append("longitude", long.toString())
                        parameters.append("radius", radius.toString())
                    }
                }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}