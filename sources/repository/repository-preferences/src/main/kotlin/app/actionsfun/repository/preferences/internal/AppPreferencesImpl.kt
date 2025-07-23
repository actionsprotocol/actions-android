package app.actionsfun.repository.preferences.internal

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber
import app.actionsfun.repository.preferences.AppPreferences
import app.actionsfun.repository.preferences.Preference

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
) : AppPreferences {

    override fun <T : Any> getValue(preference: Preference<T>): T? {
        return runBlocking {
            dataStore.data
                .catch { emit(emptyPreferences()) }
                .first()
                .get(preference)
        }
    }

    override fun <T : Any> setValue(preference: Preference<T>, value: T?) {
        runBlocking {
            dataStore.edit { prefs ->
                if (value == null) {
                    prefs.clear(preference)
                } else {
                    prefs.set(preference, value)
                }
            }
        }
    }

    override fun <T : Any> observe(preference: Preference<T>): Flow<T?> {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { prefs -> prefs.get(preference) }
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun <T : Any> Preferences.get(preference: Preference<T>): T? {
        val key = preference.key

        preference.serializer?.let { serializer ->
            return this[stringPreferencesKey(key)]?.let {
                runCatching { json.decodeFromString(serializer, it) }
                    .getOrNull()
            } ?: preference.defaultValue
        }

        return when (preference.defaultValue) {
            is String -> this[stringPreferencesKey(key)]
            is Int -> this[intPreferencesKey(key)]
            is Long -> this[longPreferencesKey(key)]
            is Float -> this[floatPreferencesKey(key)]
            is Boolean -> this[booleanPreferencesKey(key)]
            is Set<*> -> this[stringSetPreferencesKey(key)]
            else -> null
        } as T?
            ?: preference.defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> MutablePreferences.set(preference: Preference<T>, value: T) {
        val key = preference.key

        preference.serializer?.let { serializer ->
            this[stringPreferencesKey(key)] = json.encodeToString(serializer, value)
            return
        }

        when (value) {
            is String -> this[stringPreferencesKey(key)] = value
            is Int -> this[intPreferencesKey(key)] = value
            is Long -> this[longPreferencesKey(key)] = value
            is Float -> this[floatPreferencesKey(key)] = value
            is Boolean -> this[booleanPreferencesKey(key)] = value
            is Set<*> -> this[stringSetPreferencesKey(key)] = value as Set<String>
        }
    }

    private fun <T : Any> MutablePreferences.clear(preference: Preference<T>) {
        val key = preference.key
        listOf(
            stringPreferencesKey(key),
            intPreferencesKey(key),
            longPreferencesKey(key),
            floatPreferencesKey(key),
            booleanPreferencesKey(key),
            stringSetPreferencesKey(key)
        ).forEach { remove(it) }
    }
}