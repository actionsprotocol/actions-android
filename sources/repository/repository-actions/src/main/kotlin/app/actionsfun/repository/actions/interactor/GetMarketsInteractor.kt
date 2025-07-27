package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Market
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.SerializedMarket
import app.actionsfun.repository.actions.internal.api.model.ChatMessage
import app.actionsfun.repository.actions.internal.util.parseTimestamp
import app.actionsfun.repository.actions.internal.util.lamportsToSOL
import app.actionsfun.repository.actions.internal.util.toReply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

interface GetMarketsInteractor {

    suspend fun get(): List<Market>
}

internal class GetMarketsInteractorImpl(
    private val api: ActionsApi,
) : GetMarketsInteractor {

    override suspend fun get(): List<Market> {
        return withContext(Dispatchers.IO) {
            listOf(api.getMarkets()[0])
                .map { item ->
                    async { buildMarket(item.account) }
                }
                .awaitAll()
        }
    }

    private suspend fun buildMarket(market: SerializedMarket): Market {
        val chatMessages = fetchChatMessages(market.address)

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
            replies = chatMessages.map { it.toReply() }
        )
    }

    private suspend fun fetchChatMessages(marketId: String): List<ChatMessage> {
        return api.getChatMessages(marketId, limit = 50).data
    }
}
