package app.actionsfun.repository.solana.internal.core

data class ProgramDerivedAddress(
    val publicKey: PublicKey,
    val nonce: Int,
)
