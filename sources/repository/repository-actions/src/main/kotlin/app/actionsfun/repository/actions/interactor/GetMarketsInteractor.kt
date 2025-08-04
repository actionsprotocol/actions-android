package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Market
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.SerializedMarket
import app.actionsfun.repository.actions.internal.api.model.MarketResponseItem
import app.actionsfun.repository.actions.internal.util.parseTimestamp
import app.actionsfun.repository.actions.internal.util.lamportsToSOL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetMarketsInteractor {

    suspend fun get(): List<Market>
}

internal class GetMarketsInteractorImpl(
    private val api: ActionsApi,
) : GetMarketsInteractor {

    override suspend fun get(): List<Market> {
        return withContext(Dispatchers.IO) {
            api.getMarkets()
                .map(MarketResponseItem::account)
                .filter { market ->
                    market.metadataUri.isValidMetadataUri()
                            && !market.creatorTwitterUsername.isNullOrEmpty()
                }
                .mapNotNull { market ->
                    runCatching { buildMarket(market) }
                        .getOrNull()
                }
        }
    }

    private fun buildMarket(market: SerializedMarket): Market {
        return Market(
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

private fun String.isValidMetadataUri(): Boolean {
    return this.startsWith("https://")
            || this.startsWith("http://")
            || this.startsWith("ipfs://")
            || this.startsWith("pool_")
}
