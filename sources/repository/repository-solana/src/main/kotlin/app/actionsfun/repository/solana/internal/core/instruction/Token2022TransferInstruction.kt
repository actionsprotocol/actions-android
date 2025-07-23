package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.Constants.Token2022ProgramID
import app.actionsfun.repository.solana.internal.core.PublicKey

class Token2022TransferInstruction @JvmOverloads constructor(
    from: PublicKey,
    to: PublicKey,
    mint: PublicKey,
    owner: PublicKey,
    amount: Long,
    decimals: Int,
    signers: List<PublicKey> = emptyList(),
) : TokenTransferInstruction(from, to, mint, owner, amount, decimals, signers) {
    override val programId: PublicKey = Token2022ProgramID
}
