package app.actionsfun.common.network.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object AnySerializer : KSerializer<Any?> {
    override val descriptor: SerialDescriptor = JsonElement.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Any?) {
        val jsonElement = when (value) {
            null -> JsonNull
            is String -> JsonPrimitive(value)
            is Number -> JsonPrimitive(value)
            is Boolean -> JsonPrimitive(value)
            else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
        }
        encoder.encodeSerializableValue(JsonElement.serializer(), jsonElement)
    }

    override fun deserialize(decoder: Decoder): Any? {
        val jsonElement = decoder.decodeSerializableValue(JsonElement.serializer())
        return when (jsonElement) {
            is JsonNull -> null
            is JsonPrimitive -> when {
                jsonElement.isString -> jsonElement.content
                jsonElement.booleanOrNull != null -> jsonElement.boolean
                jsonElement.longOrNull != null -> jsonElement.long
                jsonElement.doubleOrNull != null -> jsonElement.double
                else -> jsonElement.content
            }
            else -> throw IllegalArgumentException("Complex types not supported")
        }
    }
}