package app.actionsfun.common.network.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull
import java.time.temporal.TemporalAccessor

object MapStringAnySerializer : KSerializer<Map<String, Any?>> {

    @Serializable
    private abstract class MapAnyMap : Map<String, Any?>

    override val descriptor: SerialDescriptor = MapAnyMap.serializer().descriptor

    override fun deserialize(decoder: Decoder): Map<String, Any?> {
        if (decoder is JsonDecoder) {
            val jsonObject = decoder.decodeJsonElement() as JsonObject
            return jsonObject.toPrimitiveMap()
        } else {
            throw NotImplementedError("Decoder $decoder is not supported!")
        }
    }

    override fun serialize(encoder: Encoder, value: Map<String, Any?>) {
        if (encoder is JsonEncoder) {
            encoder.encodeJsonElement(value.toJsonElement())
        } else {
            throw NotImplementedError("Encoder $encoder is not supported!")
        }
    }

}

fun Any?.toJsonElement(): JsonElement = when (this) {
    null -> JsonNull
    is JsonElement -> this
    is Number -> JsonPrimitive(this)
    is String -> JsonPrimitive(this)
    is Boolean -> JsonPrimitive(this)
    is Enum<*> -> JsonPrimitive(this.toString())
    is TemporalAccessor -> JsonPrimitive(this.toString())
    is Array<*> -> this.toJsonElement()
    is Iterable<*> -> this.toJsonElement()
    is Map<*, *> -> this.toJsonElement()
    else -> throw IllegalStateException("Can't serialize unknown type: $this")
}

private fun Iterable<*>.toJsonElement() =
    JsonArray(this.map { it.toJsonElement() })

private fun Array<*>.toJsonElement() =
    JsonArray(this.map { it.toJsonElement() })

private fun Map<*, *>.toJsonElement() =
    JsonObject(this.map { (key, value) -> key as String to value.toJsonElement() }.toMap())

private fun JsonElement.toPrimitive(): Any? = when (this) {
    is JsonNull -> null
    is JsonObject -> this.toPrimitiveMap()
    is JsonArray -> this.toPrimitiveList()
    is JsonPrimitive -> {
        if (isString) {
            contentOrNull
        } else {
            booleanOrNull ?: longOrNull ?: doubleOrNull
        }
    }
    else -> null
}

private fun JsonObject.toPrimitiveMap(): Map<String, Any?> =
    this.map { (key, value) -> key to value.toPrimitive() }.toMap()

private fun JsonArray.toPrimitiveList(): List<Any?> =
    this.map { it.toPrimitive() }