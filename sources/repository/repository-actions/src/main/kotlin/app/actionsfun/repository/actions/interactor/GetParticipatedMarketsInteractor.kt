package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Market
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.util.lamportsToSOL
import app.actionsfun.repository.actions.internal.util.parseTimestamp
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetParticipatedMarketsInteractor {

    suspend fun get(): List<Market>
}

internal class GetParticipatedMarketsInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : GetParticipatedMarketsInteractor {

    override suspend fun get(): List<Market> {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected"
            }

            val marketData = api.getParticipatedMarkets(wallet.publicKey)
                .data.orEmpty()

            marketData.map { market ->
                Market(
                    address = market.address,
                    title = market.name,
                    description = market.description,
                    image = market.metadataUri,
                    creatorTwitterUsername = market.creatorTwitterUsername.orEmpty(),
                    creatorTwitterImage = market.creatorTwitterImage.orEmpty(),
                    createdAt = market.createdTimestamp.parseTimestamp(),
                    endsAt = market.expiryTimestamp.parseTimestamp(),
                    uiState = market.uiState,
                    volume = market.totalMarketSize.lamportsToSOL(),
                    yesVolume = market.yesAmount.lamportsToSOL(),
                    noVolume = market.noAmount.lamportsToSOL(),
                    replies = emptyList(),
                    participants = emptyList(),
                )
            }
        }
    }
}