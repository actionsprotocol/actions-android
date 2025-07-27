package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionRequest
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DepositToMarketInteractor {

    suspend fun deposit(marketId: String, amount: Long, option: Boolean)
}

internal class DepositToMarketInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : DepositToMarketInteractor {

    override suspend fun deposit(marketId: String, amount: Long, option: Boolean) {
        withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected."
            }

            val instructions = api.generateInstruction(
                request = GenerateInstructionRequest(
                    type = "makePrediction",
                    params = mapOf(
                        "marketAddress" to marketId,
                        "option" to option,
                        "amountLamports" to amount,
                        "participantAddress" to wallet.publicKey,
                    )
                )
            ).data


        }
    }
}