package app.actions.di

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val AppModule = module {
    single<SharedPreferences>(
        qualifier = StringQualifier("AppPreferences")
    ) {
        androidContext().getSharedPreferences(
            "AppPreferences",
            Context.MODE_PRIVATE
        )
    }
    single<Json>(
        qualifier = StringQualifier("AppJson")
    ) {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
            coerceInputValues = true
        }
    }
}
