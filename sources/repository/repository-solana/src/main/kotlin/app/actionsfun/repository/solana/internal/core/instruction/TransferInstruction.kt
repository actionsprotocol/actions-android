package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.Binary
import app.actionsfun.repository.solana.internal.core.Constants.SystemProgram
import app.actionsfun.repository.solana.internal.core.PublicKey

class TransferInstruction(
    from: PublicKey,
    to: PublicKey,
    private val lamports: Long,
) : Instruction {
    override val data: ByteArray
        get() {
            val instruction = Binary.uint32(2L)
            val lamports = Binary.int64(this.lamports)
            return instruction + lamports
        }

    override val keys: List<AccountMeta> = listOf(
        AccountMeta(from, writable = true, signer = true),
        AccountMeta(to, writable = true, signer = false),
    )

    override val programId: PublicKey = SystemProgram
}
