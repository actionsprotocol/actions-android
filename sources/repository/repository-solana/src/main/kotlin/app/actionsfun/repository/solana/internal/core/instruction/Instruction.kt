package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.PublicKey

interface Instruction {
    val data: ByteArray
    val keys: List<AccountMeta>
    val programId: PublicKey
}
