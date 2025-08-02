package app.actionsfun.feature.profile.domain

import app.actionsfun.repository.actions.interactor.GetMarketClaimStatusInteractor
import app.actionsfun.repository.actions.interactor.GetParticipatedMarketsInteractor

internal class GetUserParticipatedMarketsWithClaimStatus(
    private val getParticipatedMarkets: GetParticipatedMarketsInteractor,
    private val getClaimStatus: GetMarketClaimStatusInteractor,
) {
    suspend fun get(): List<MarketWithClaimStatus> {
        return getParticipatedMarkets.get().map { market ->
            val claimStatus = getClaimStatus.get(market.address)

            MarketWithClaimStatus(
                market = market,
                claimStatus = claimStatus,
            )
        }
    }
}
