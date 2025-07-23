package app.actionsfun.repository.preferences

import android.content.Context
import androidx.annotation.MainThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppPreferences")

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Preference<*>> AppPreferences.get(): Any? {
    val instance = T::class.objectInstance
        ?: throw IllegalArgumentException("${T::class} is not an object")

    return getValue(instance as Preference<Any>)
}

@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Preference<*>> AppPreferences.set(value: Any?) {
    val instance = T::class.objectInstance
        ?: throw IllegalArgumentException("${T::class} is not an object")

    setValue(instance as Preference<Any>, value)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Preference<*>> AppPreferences.observe(): Flow<Any?> {
    val instance = T::class.objectInstance
        ?: throw IllegalArgumentException("${T::class} is not an object")

    return observe(instance as Preference<Any>)
}

@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Preference<*>> AppPreferences.update(
    crossinline transform: (Any?) -> Any?
) {
    val instance = T::class.objectInstance
        ?: throw IllegalArgumentException("${T::class} is not an object")

    val preference = instance as Preference<Any>
    val currentValue = getValue(preference) ?: preference.defaultValue
    val newValue = transform(currentValue)
    setValue(preference, newValue)
}
