package group.one.sos.data.remote

import android.util.Log
import group.one.sos.domain.models.ApiResponse
import group.one.sos.domain.models.EmergencyResponse
import group.one.sos.domain.models.EmergencyType
import group.one.sos.domain.models.IncidentResponse
import group.one.sos.domain.models.IncidentType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.io.encoding.ExperimentalEncodingApi

class RemoteApiService {
    private val httpClient = HttpClient(OkHttp) {
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

    suspend fun getIncidents(
        lat: Double,
        long: Double,
        radius: Int
    ): Result<List<IncidentResponse>> {
        return try {
            val response: ApiResponse<List<IncidentResponse>> =
                httpClient.get("$baseURL/incidents") {
                    url {
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

    @OptIn(ExperimentalEncodingApi::class, InternalAPI::class)
    suspend fun reportIncident(
        incidentType: IncidentType,
        description: String,
        photo: File,
        lat: Double,
        long: Double,
    ): Result<IncidentResponse> {
        return try {
            Log.d("Upload", "Path: ${photo.path}")
            Log.d("Upload", "Name: ${photo.name}")
            Log.d("Upload", "Size: ${photo.length()} bytes")
            Log.d("Upload", "Exists: ${photo.exists()}")

            val file = File(photo.path)
            val bytes = file.readBytes()

            val response: ApiResponse<IncidentResponse> = httpClient.post("$baseURL/incidentReport") {
                header(HttpHeaders.Accept, "application/json")
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("typeOfIncident", incidentType.toString().lowercase())
                            append("description", description)
                            append("latitude", lat.toString())
                            append("longitude", long.toString())

                            append(
                                "pictures",
                                bytes,
                                Headers.build {
                                    append(HttpHeaders.ContentType, "image/jpeg")
                                    append(HttpHeaders.ContentDisposition, "form-data; name=\"pictures\"; filename=\"report.jpeg\"")
                                }
                            )
                        },
                    )
                )
            }.body()
//                formData = formData {
//                    append("typeOfIncident", incidentType.toString().lowercase())
//                    append("description", description)
//                    append("latitude", lat.toString())
//                    append("longitude", long.toString())
//
//                    append(
//                        key = "pictures",
//                        headers = Headers.build {
//                            append(HttpHeaders.ContentDisposition, "form-data; name=\"pictures\"; filename=\"${photo.name}\"")
//                            append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
//                        },
//                        value = InputProvider { photo.inputStream().asInput() }
//                    )
//                }
//            ).body()

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