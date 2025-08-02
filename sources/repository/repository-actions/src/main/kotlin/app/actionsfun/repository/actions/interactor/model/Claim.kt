package app.actionsfun.repository.actions.interactor.model

import app.actionsfun.repository.actions.internal.api.model.MarketStatus

data class Claim(
    val canClaim: Boolean,
    val alreadyClaimed: Boolean,
    val claimableAmount: String,
    val claimableAmountSol: Float,
    val multiplier: Float,
    val userBet: String,
    val userBetSol: Float,
    val userOption: Boolean,
    val winningOption: Boolean,
    val poolStatus: MarketStatus? = null,
)
