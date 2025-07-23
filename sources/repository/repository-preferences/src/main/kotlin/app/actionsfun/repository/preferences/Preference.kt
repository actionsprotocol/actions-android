package app.actionsfun.repository.preferences

import kotlinx.serialization.KSerializer

interface Preference<T : Any> {
    val key: String
    val defaultValue: T?
    val serializer: KSerializer<T>? get() = null
}
