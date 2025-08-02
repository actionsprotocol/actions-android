package app.actionsfun.feature.profile.domain

import app.actionsfun.repository.actions.interactor.model.Claim
import app.actionsfun.repository.actions.interactor.model.Market

internal data class MarketWithClaimStatus(
    val market: Market,
    val claimStatus: Claim,
)
