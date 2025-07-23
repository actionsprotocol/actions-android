package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.Binary
import app.actionsfun.repository.solana.internal.core.PublicKey
import java.io.ByteArrayOutputStream

abstract class TokenTransferInstruction @JvmOverloads constructor(
    val from: PublicKey,
    val to: PublicKey,
    val mint: PublicKey,
    val owner: PublicKey,
    val amount: Long,
    val decimals: Int,
    val signers: List<PublicKey> = emptyList(),
) : Instruction {
    companion object {
        @Suppress("unused")
        private const val InstructionTransfer = 3
        private const val InstructionTransferChecked = 12
    }

    override val data: ByteArray
        get() {
            ByteArrayOutputStream().use { buffer ->
                buffer.write(InstructionTransferChecked)
                buffer.write(Binary.int64(amount))
                buffer.write(decimals)
                return buffer.toByteArray()
            }
        }

    override val keys: List<AccountMeta> = listOf(
        AccountMeta.writable(from),
        AccountMeta(mint),
        AccountMeta.writable(to),
        if (signers.isEmpty()) AccountMeta.signer(owner) else AccountMeta(owner),
        *signers.map { s -> AccountMeta.signer(s) }.toTypedArray(),
    )
}
