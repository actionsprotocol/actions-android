package app.actionsfun.common.network.di

import app.actionsfun.common.network.connection.NetworkConnectionObserver
import app.actionsfun.common.network.connection.NetworkConnectionObserverImpl
import app.actionsfun.common.network.ip.UserIPProvider
import app.actionsfun.common.network.ip.UserIPProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val CommonNetworkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    single<NetworkConnectionObserver> {
        NetworkConnectionObserverImpl(
            context = get()
        )
    }

    single<UserIPProvider> {
        UserIPProviderImpl()
    }
}