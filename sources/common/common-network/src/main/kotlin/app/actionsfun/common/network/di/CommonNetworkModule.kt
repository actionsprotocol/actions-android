package app.actionsfun.common.network.di

import app.actionsfun.common.network.connection.NetworkConnectionObserver
import app.actionsfun.common.network.connection.NetworkConnectionObserverImpl
import app.actionsfun.common.network.geo.UserGeoRepository
import app.actionsfun.common.network.geo.internal.UserGeoRepositoryImpl
import app.actionsfun.common.network.ip.UserIPProvider
import app.actionsfun.common.network.ip.UserIPProviderImpl
import app.actionsfun.common.network.speedtest.NetworkSpeedRepository
import app.actionsfun.common.network.speedtest.internal.NetworkSpeedRepositoryImpl
import app.actionsfun.common.network.speedtest.internal.api.SpeedtestApi
import app.actionsfun.common.network.speedtest.internal.download.HttpDownloadTest
import app.actionsfun.common.network.speedtest.internal.upload.HttpUploadTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
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

    single<SpeedtestApi> {
        Retrofit.Builder()
            .baseUrl("https://www.speedtest.net/")
            .client(get())
            .build()
            .create()
    }

    single<UserGeoRepository> {
        UserGeoRepositoryImpl(
            speedtestApi = get()
        )
    }

    single<NetworkSpeedRepository> {
        NetworkSpeedRepositoryImpl(
            api = get(),
            httpUploadTest = HttpUploadTest(),
            httpDownloadTest = HttpDownloadTest(),
        )
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