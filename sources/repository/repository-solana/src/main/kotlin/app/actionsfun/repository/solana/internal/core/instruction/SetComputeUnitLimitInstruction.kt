package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.Constants.ComputeBudgetProgramID
import app.actionsfun.repository.solana.internal.core.PublicKey

data class SetComputeUnitLimitInstruction(
    val units: Long,
) : Instruction {
    override val data: ByteArray = ByteArray(9).apply {
        this[0] = 2
        for (i in 0 until 8) {
            this[i + 1] = ((units shr (i * 8)) and 0xFF).toByte()
        }
    }

    override val keys: List<AccountMeta> = emptyList()

    override val programId: PublicKey = ComputeBudgetProgramID
}
