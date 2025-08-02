package app.actionsfun.repository.solana.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import app.actionsfun.common.util.startWith
import app.actionsfun.repository.preferences.AppPreferences
import app.actionsfun.repository.preferences.get
import app.actionsfun.repository.preferences.observe
import app.actionsfun.repository.preferences.set
import app.actionsfun.repository.solana.WalletRepository
import app.actionsfun.repository.solana.internal.core.PublicKey
import app.actionsfun.repository.solana.internal.core.Connection
import app.actionsfun.repository.solana.ConnectedWallet
import app.actionsfun.repository.solana.internal.core.api.Commitment
import app.actionsfun.repository.solana.internal.core.rpc.TokenAmount
import app.actionsfun.repository.solana.internal.wallet.ConnectedWalletPreference
import app.actionsfun.repository.solana.internal.wallet.MobileWalletAdapterWrapper
import app.actionsfun.repository.user.UserRepository
import com.solana.transaction.Transaction
import java.math.BigInteger

internal class WalletRepositoryImpl(
    private val appPreferences: AppPreferences,
    private val mobileWalletAdapter: MobileWalletAdapterWrapper,
    private val userRepository: UserRepository,
    private val connection: Connection,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : WalletRepository {

    override val wallet = MutableSharedFlow<ConnectedWallet>(replay = 1)

    init {
        appPreferences.observe<ConnectedWalletPreference>()
            .filterIsInstance<ConnectedWallet>()
            .startWith(ConnectedWallet.Empty)
            .onEach(wallet::emit)
            .launchIn(coroutineScope)
    }

    override suspend fun connectWallet() {
        val auth = mobileWalletAdapter.connectWallet()
            ?: error("Wallet connection failed")
        val user = userRepository.getUser()

        val wallet = with(auth) {
            ConnectedWallet(
                userId = user.id,
                authToken = authToken,
                publicKey = PublicKey(publicKey).toBase58(),
                accountLabel = accountLabel.orEmpty(),
                accounts = accounts.map { account ->
                    ConnectedWallet.Account(
                        publicKey = PublicKey(account.publicKey).toBase58(),
                        accountLabel = account.accountLabel.orEmpty(),
                    )
                }
            )
        }

        withContext(Dispatchers.Main) {
            appPreferences.set<ConnectedWalletPreference>(wallet)
        }
    }

    override suspend fun getWallet(): ConnectedWallet {
        return withContext(Dispatchers.IO) {
            appPreferences.get<ConnectedWalletPreference>() as? ConnectedWallet
                ?: ConnectedWallet.Empty
        }
    }

    override suspend fun disconnectWallet() {
        withContext(Dispatchers.IO) {
            mobileWalletAdapter.clearAuthToken()

            withContext(Dispatchers.Main) {
                appPreferences.set<ConnectedWalletPreference>(null)
            }
        }
    }

    override suspend fun signMessage(message: ByteArray): ByteArray {
        return withContext(Dispatchers.IO) {
            val connectedWallet = getWallet()

            mobileWalletAdapter.signMessage(
                message = message,
                addresses = arrayOf(PublicKey(connectedWallet.publicKey).bytes())
            )
        }
    }

    override suspend fun getTokenBalance(mint: String): TokenAmount? {
        return withContext(Dispatchers.IO) {
            runCatching { getWallet().publicKey }
                .mapCatching(::PublicKey)
                .mapCatching(connection::getTokenAccountsByOwner)
                .mapCatching { accounts ->
                    accounts.find { account ->
                        account.account.data.parsed.info.mint == mint
                    }
                }
                .mapCatching { account ->
                    account?.account?.data?.parsed?.info?.tokenAmount
                }
                .getOrDefault(null)
        }
    }

    override suspend fun getBalance(): BigInteger? {
        return withContext(Dispatchers.IO) {
            runCatching { getWallet().publicKey }
                .mapCatching(::PublicKey)
                .mapCatching(connection::getBalance)
                .getOrDefault(null)
        }
    }

    override suspend fun getLatestBlockhash(): String {
        return withContext(Dispatchers.IO) {
            connection.getLatestBlockhash(Commitment.Confirmed)
        }
    }

    override suspend fun sendAndConfirmTransaction(transaction: Transaction): String {
        return withContext(Dispatchers.IO) {
            val signatures = mobileWalletAdapter.signAndSendTransactions(
                transactions = arrayOf(transaction.serialize())
            )
            signatures.firstOrNull() ?: error("No transaction signature returned")
        }
    }
}
