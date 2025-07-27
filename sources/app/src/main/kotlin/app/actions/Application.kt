package app.actions

import android.app.Application
import app.actionsfun.common.network.di.CommonNetworkModule
import app.actions.di.AppModule
import app.actionsfun.feature.onboarding.di.OnboardingModule
import app.actionsfun.feature.splash.di.SplashModule
import app.actionsfun.repository.actions.di.ActionsRepositoryModule
import app.actionsfun.repository.onboarding.di.OnboardingRepositoryModule
import app.actionsfun.repository.preferences.di.AppPreferencesModule
import app.actionsfun.repository.solana.di.SolanaModule
import app.actionsfun.repository.user.di.UserRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                AppModule,
                AppPreferencesModule,
                CommonNetworkModule,
                SolanaModule,
                UserRepositoryModule,
                OnboardingRepositoryModule,
                SplashModule,
                OnboardingModule,
                ActionsRepositoryModule,
            )
        }
    }
}