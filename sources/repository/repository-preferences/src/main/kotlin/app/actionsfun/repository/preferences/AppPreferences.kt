package app.actionsfun.repository.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferences {

    fun <T : Any> getValue(preference: Preference<T>): T?

    fun <T : Any> setValue(preference: Preference<T>, value: T?)

    fun <T : Any> observe(preference: Preference<T>): Flow<T?>
}
