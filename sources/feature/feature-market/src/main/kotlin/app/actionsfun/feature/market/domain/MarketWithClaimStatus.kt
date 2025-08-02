package app.actionsfun.feature.market.domain

import app.actionsfun.repository.actions.interactor.model.Claim
import app.actionsfun.repository.actions.interactor.model.Market

internal data class MarketWithClaimStatus(
    val market: Market,
    val claimStatus: Claim?,
)