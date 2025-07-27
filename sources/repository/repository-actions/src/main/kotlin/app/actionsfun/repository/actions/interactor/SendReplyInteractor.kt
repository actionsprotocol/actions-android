package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.AddChatMessageRequest
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SendReplyInteractor {

    suspend fun send(marketId: String, reply: String)
}

internal class SendReplyInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : SendReplyInteractor {

    override suspend fun send(marketId: String, reply: String) {
        withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected."
            }

            api.addChatMessage(
                marketId = marketId,
                request = AddChatMessageRequest(
                    publicKey = wallet.publicKey,
                    username = wallet.publicKey.take(8),
                    content = reply,
                )
            )
        }
    }
}
