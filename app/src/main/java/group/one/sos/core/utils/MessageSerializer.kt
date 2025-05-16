package group.one.sos.core.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

object MessageSerializer : KSerializer<String> {
    override val descriptor = PrimitiveSerialDescriptor("Message", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val input = decoder as? JsonDecoder ?: error("Can only deserialize JSON")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> element.content
            is JsonArray -> element.joinToString("; ") { it.jsonPrimitive.content }
            else -> "Unknown message"
        }
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}
