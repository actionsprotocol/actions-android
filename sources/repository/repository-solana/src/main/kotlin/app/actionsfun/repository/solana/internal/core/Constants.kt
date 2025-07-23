@file:Suppress("ConstPropertyName")

package app.actionsfun.repository.solana.internal.core

object Constants {
    @JvmStatic
    val SystemProgram = PublicKey("11111111111111111111111111111111")

    @JvmStatic
    val TokenProgramID = PublicKey("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA")

    @JvmStatic
    val Token2022ProgramID = PublicKey("TokenzQdBNbLqP5VEhdkAS6EPFLC1PHnBqCXEpPxuEb")

    @JvmStatic
    val AssociatedTokenProgramID = PublicKey("ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL")

    @JvmStatic
    val ComputeBudgetProgramID = PublicKey("ComputeBudget111111111111111111111111111111")

    @JvmStatic
    val SysvarRentAddress = PublicKey("SysvarRent111111111111111111111111111111111")

    const val PublicKeyLength = 32
    const val SignatureLength = 64
}
