package group.one.sos.data.remote

import android.util.Log
import group.one.sos.domain.models.ApiResponse
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorLog", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    private val baseURL = "https://sosbackend-api.onrender.com/api"

     suspend fun getEmergencyServices(
        responder: EmergencyType,
        radius: Int,
        lat: Double,
        long: Double
    ): Result<List<EmergencyResponse>> {
        return try {
            val response: ApiResponse<List<EmergencyResponse>> =
                httpClient.get("$baseURL/emergencyServices") {
                    url {
                        parameters.append("type", responder.name)
                        parameters.append("latitude", lat.toString())
                        parameters.append("longitude", long.toString())
                        parameters.append("radius", radius.toString())
                    }
                }.body()

            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}