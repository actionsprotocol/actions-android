package app.actionsfun.repository.preferences.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import app.actionsfun.repository.preferences.AppPreferences
import app.actionsfun.repository.preferences.dataStore
import app.actionsfun.repository.preferences.internal.AppPreferencesImpl

val AppPreferencesModule = module {
    single<AppPreferences> {
        AppPreferencesImpl(
            dataStore = androidContext().dataStore,
            json = get(qualifier = StringQualifier("AppJson")),
        )
    }
}
