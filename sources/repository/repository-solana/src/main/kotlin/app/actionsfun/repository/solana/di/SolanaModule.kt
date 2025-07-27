package app.actionsfun.repository.solana.di

import androidx.core.net.toUri
import app.actionsfun.repository.solana.BuildConfig
import com.solana.mobilewalletadapter.clientlib.ConnectionIdentity
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.Solana
import org.koin.dsl.module
import app.actionsfun.repository.solana.WalletRepository
import app.actionsfun.repository.solana.activity.ActivityResultSenderProvider
import app.actionsfun.repository.solana.internal.WalletRepositoryImpl
import app.actionsfun.repository.solana.internal.core.Connection
import app.actionsfun.repository.solana.internal.core.RpcUrl
import app.actionsfun.repository.solana.internal.wallet.MobileWalletAdapterWrapper

val SolanaModule = module {

    single<ActivityResultSenderProvider>(
        createdAtStart = true,
    ) {
        ActivityResultSenderProvider(get())
    }

    single<MobileWalletAdapter> {
        MobileWalletAdapter(
            connectionIdentity = ConnectionIdentity(
                identityUri = "https://actions.fun".toUri(),
                iconUri = "favicon.ico".toUri(),
                identityName = "actions.fun",
            )
        )
    }

    single {
        MobileWalletAdapterWrapper(
            activityResultSenderProvider = get(),
            walletAdapter = get(),
            blockchain = if (BuildConfig.DEBUG) Solana.Devnet else Solana.Mainnet,
        )
    }

    single<WalletRepository> {
        WalletRepositoryImpl(
            mobileWalletAdapter = get(),
            userRepository = get(),
            appPreferences = get(),
            connection = Connection(if (BuildConfig.DEBUG) RpcUrl.Devnet else RpcUrl.Mainnet)
        )
    }
}