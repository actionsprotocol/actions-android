package app.actionsfun.repository.solana.internal.core.api

import java.math.BigInteger

data class TokenAccountBalance(
    val amount: BigInteger,
    val decimals: Int,
    val uiAmount: String,
)
