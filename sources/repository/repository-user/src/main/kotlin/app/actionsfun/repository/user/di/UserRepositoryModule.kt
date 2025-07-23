package app.actionsfun.repository.user.di

import org.koin.dsl.module
import app.actionsfun.repository.user.UserRepository
import app.actionsfun.repository.user.internal.LocalUserRepository

val UserRepositoryModule = module {
    single<UserRepository> {
        LocalUserRepository(
            appPreferences = get()
        )
    }
}