package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Claim
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetMarketClaimStatusInteractor {

    suspend fun get(marketAddress: String): Claim
}

internal class GetMarketClaimStatusInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : GetMarketClaimStatusInteractor {

    override suspend fun get(marketAddress: String): Claim {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected"
            }

            val claimData = api.getMarketClaim(
                marketId = marketAddress,
                userAddress = wallet.publicKey,
            ).data

            Claim(
                canClaim = claimData?.canClaim ?: false,
                alreadyClaimed = claimData?.alreadyClaimed ?: false,
                claimableAmount = claimData?.claimableAmount ?: "0",
                claimableAmountSol = claimData?.claimableAmountSol ?: 0.0,
                multiplier = claimData?.multiplier ?: 0.0,
                userBet = claimData?.userBet ?: "0",
                userBetSol = claimData?.userBetSol ?: 0.0,
                userOption = claimData?.userOption ?: false,
                winningOption = claimData?.winningOption ?: false,
            )
        }
    }
}