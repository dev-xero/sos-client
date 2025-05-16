package group.one.sos.domain.models

import group.one.sos.core.utils.MessageSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: @Serializable(with = MessageSerializer::class) String,
    val data: T? = null
)
