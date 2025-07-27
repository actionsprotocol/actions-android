package app.actionsfun.repository.actions.di

import app.actionsfun.repository.actions.interactor.GetMarketsInteractor
import app.actionsfun.repository.actions.interactor.GetMarketsInteractorImpl
import app.actionsfun.repository.actions.internal.api.ActionsApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

val ActionsRepositoryModule = module {
    single<ActionsApi> {
        val json: Json = get(qualifier = StringQualifier("AppJson"))

        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType())
            )
            .baseUrl("https://actions-backend-mainnet.onrender.com/")
            .client(get())
            .build()
            .create()
    }

    single<GetMarketsInteractor> {
        GetMarketsInteractorImpl(
            api = get(),
        )
    }
}