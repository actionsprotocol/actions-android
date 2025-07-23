package app.actionsfun.repository.solana.internal.core.instruction

import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.Constants.AssociatedTokenProgramID
import app.actionsfun.repository.solana.internal.core.Constants.SystemProgram
import app.actionsfun.repository.solana.internal.core.Constants.Token2022ProgramID
import app.actionsfun.repository.solana.internal.core.PublicKey

class CreateAssociatedToken2022AccountInstruction(
    payer: PublicKey,
    associatedToken: PublicKey,
    owner: PublicKey,
    mint: PublicKey,
) : Instruction {

    override val data: ByteArray = byteArrayOf(0)

    override val keys: List<AccountMeta> = listOf(
        AccountMeta.signerAndWritable(payer),
        AccountMeta.writable(associatedToken),
        AccountMeta(owner),
        AccountMeta(mint),
        AccountMeta(SystemProgram),
        AccountMeta(Token2022ProgramID),
    )

    override val programId: PublicKey = AssociatedTokenProgramID
}
