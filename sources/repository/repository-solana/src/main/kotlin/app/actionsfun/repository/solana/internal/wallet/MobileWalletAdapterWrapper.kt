package app.actionsfun.repository.solana.internal.wallet

import com.solana.mobilewalletadapter.clientlib.Blockchain
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.Solana
import com.solana.mobilewalletadapter.clientlib.TransactionResult
import com.solana.mobilewalletadapter.clientlib.protocol.MobileWalletAdapterClient.AuthorizationResult
import com.solana.mobilewalletadapter.clientlib.successPayload
import timber.log.Timber
import app.actionsfun.repository.solana.activity.ActivityResultSenderProvider
import app.actionsfun.repository.solana.internal.core.Keypair
import app.actionsfun.repository.solana.internal.core.Transaction

internal class MobileWalletAdapterWrapper(
    private val activityResultSenderProvider: ActivityResultSenderProvider,
    private val walletAdapter: MobileWalletAdapter,
    blockchain: Blockchain,
) {
    init {
        walletAdapter.blockchain = blockchain
    }

    suspend fun connectWallet(): AuthorizationResult? {
        val activityResultSender = activityResultSenderProvider.activityResultSender
            ?: error("No activity result sender found.")

        return walletAdapter.transact(activityResultSender) { it }
            .let { transactionResult ->
                when (transactionResult) {
                    is TransactionResult.Success -> transactionResult.successPayload
                    else -> error("Transaction failed.")
                }
            }
    }

    suspend fun signMessage(
        message: ByteArray,
        addresses: Array<ByteArray>
    ): ByteArray {
        val activityResultSender = activityResultSenderProvider.activityResultSender
            ?: error("No activity result sender found.")

        return walletAdapter.transact(activityResultSender) {
            val signResult = signMessagesDetached(
                messages = arrayOf(message),
                addresses = addresses
            )
            signResult.messages.first().signatures.first()
        }.let { transactionResult ->
            when (transactionResult) {
                is TransactionResult.Success -> transactionResult.payload
                else -> error("Signing failed.")
            }
        }
    }

    fun setAuthToken(token: String) {
        walletAdapter.authToken = token
    }

    fun clearAuthToken() {
        walletAdapter.authToken = null
    }
}