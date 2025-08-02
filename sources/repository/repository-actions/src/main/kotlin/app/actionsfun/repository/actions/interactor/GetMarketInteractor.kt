package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Market
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.ChatMessage
import app.actionsfun.repository.actions.internal.api.model.SerializedMarket
import app.actionsfun.repository.actions.internal.api.model.SerializedParticipant
import app.actionsfun.repository.actions.internal.util.lamportsToSOL
import app.actionsfun.repository.actions.internal.util.parseTimestamp
import app.actionsfun.repository.actions.internal.util.toParticipant
import app.actionsfun.repository.actions.internal.util.toReply
import app.actionsfun.repository.pinata.interactor.GetPinataMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetMarketInteractor {

    suspend fun get(marketAddress: String) : Market
}

internal class GetMarketInteractorImpl(
    private val api: ActionsApi,
    private val getPinataMetadata: GetPinataMetadata,
) : GetMarketInteractor {

    override suspend fun get(marketAddress: String): Market {
        return withContext(Dispatchers.IO) {
            val market = api.getMarket(marketId = marketAddress)
            buildMarket(market.account, market.participants.orEmpty())
        }
    }

    private suspend fun buildMarket(
        market: SerializedMarket,
        participants: List<SerializedParticipant>,
    ): Market {
        val chatMessages = fetchChatMessages(market.address)
        val metadata = getPinataMetadata.get(market.metadataUri)

        return Market(
            address = market.address,
            title = market.name,
            description = market.description,
            image = metadata.image,
            creatorTwitterUsername = market.creatorTwitterUsername.orEmpty(),
            creatorTwitterImage = market.creatorTwitterImage.orEmpty(),
            createdAt = market.createdTimestamp.parseTimestamp(),
            endsAt = market.expiryTimestamp.parseTimestamp(),
            uiState = market.uiState,
            volume = market.totalMarketSize.lamportsToSOL(),
            yesVolume = market.yesAmount.lamportsToSOL(),
            noVolume = market.noAmount.lamportsToSOL(),
            replies = chatMessages.map { it.toReply() },
            participants = participants.map { it.toParticipant() },
        )
    }

    private suspend fun fetchChatMessages(marketId: String): List<ChatMessage> {
        return api.getChatMessages(marketId, limit = 50).data
    }
}