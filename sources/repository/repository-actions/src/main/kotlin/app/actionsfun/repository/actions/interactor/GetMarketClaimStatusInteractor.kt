package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Claim
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetMarketClaimStatusInteractor {

    suspend fun get(marketAddress: String): Claim?
}

internal class GetMarketClaimStatusInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : GetMarketClaimStatusInteractor {

    override suspend fun get(marketAddress: String): Claim? {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected"
            }

            val claimData = api.getMarketClaim(
                marketId = marketAddress,
                userAddress = wallet.publicKey,
            ).data ?: return@withContext null

            Claim(
                canClaim = claimData.canClaim,
                alreadyClaimed = claimData.alreadyClaimed,
                claimableAmount = claimData.claimableAmount,
                claimableAmountSol = claimData.claimableAmountSol,
                multiplier = claimData.multiplier,
                userBet = claimData.userBet,
                userBetSol = claimData.userBetSol,
                userOption = claimData.userOption,
                winningOption = claimData.winningOption,
            )
        }
    }
}