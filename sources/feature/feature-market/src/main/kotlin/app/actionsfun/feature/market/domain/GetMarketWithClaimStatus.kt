package app.actionsfun.feature.market.domain

import app.actionsfun.repository.actions.interactor.GetMarketClaimStatusInteractor
import app.actionsfun.repository.actions.interactor.GetMarketInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetMarketWithClaimStatus(
    private val getMarketInteractor: GetMarketInteractor,
    private val getClaimStatusInteractor: GetMarketClaimStatusInteractor,
) {

    suspend fun get(marketAddress: String): MarketWithClaimStatus {
        return withContext(Dispatchers.IO) {
            val market = getMarketInteractor.get(marketAddress)
            val claimStatus = runCatching { getClaimStatusInteractor.get(marketAddress) }.getOrNull()
            MarketWithClaimStatus(market, claimStatus)
        }
    }
}