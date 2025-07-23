package app.actionsfun.repository.solana.internal.core.api

data class Blockhash(
    val blockhash: String,
    val slot: Long,
    val lastValidBlockHeight: Long,
)
