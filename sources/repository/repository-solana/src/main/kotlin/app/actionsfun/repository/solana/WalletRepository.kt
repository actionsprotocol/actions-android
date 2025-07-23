package app.actionsfun.repository.solana

import app.actionsfun.repository.solana.internal.core.rpc.TokenAmount
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    val wallet: Flow<ConnectedWallet>

    suspend fun getWallet(): ConnectedWallet

    suspend fun getTokenBalance(mint: String): TokenAmount?

    suspend fun signMessage(message: ByteArray): ByteArray

    suspend fun connectWallet()

    suspend fun disconnectWallet()
}