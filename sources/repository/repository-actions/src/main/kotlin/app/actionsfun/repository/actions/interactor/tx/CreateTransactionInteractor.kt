package app.actionsfun.repository.actions.interactor.tx

import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionData
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionRequest
import app.actionsfun.repository.actions.internal.api.model.InstructionData
import app.actionsfun.repository.solana.WalletRepository
import app.actionsfun.repository.solana.internal.core.AccountMeta
import app.actionsfun.repository.solana.internal.core.PublicKey
import app.actionsfun.repository.solana.internal.core.Transaction
import app.actionsfun.repository.solana.internal.core.instruction.BaseInstruction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface CreateTransactionInteractor {

    suspend fun createDepositToMarketTransaction(
        marketId: String,
        lamports: Long,
        option: Boolean,
    ): ByteArray
}

internal class CreateTransactionInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : CreateTransactionInteractor {

    override suspend fun createDepositToMarketTransaction(
        marketId: String,
        lamports: Long,
        option: Boolean
    ): ByteArray {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected."
            }

            val instructionData = generateInstructions(
                type = "makePrediction",
                params = mapOf(
                    "marketAddress" to marketId,
                    "option" to option,
                    "amountLamports" to lamports,
                    "participantAddress" to wallet.publicKey,
                )
            )

            val transaction = buildTransaction(wallet.publicKey, instructionData.instructions)

            transaction.serialize()
        }
    }

    private suspend fun buildTransaction(
        publicKey: String,
        instructionsData: List<InstructionData>
    ): Transaction {
        val blockhash = walletRepository.getLatestBlockhash()
        val instructions = instructionsData.map { apiInstruction ->
            BaseInstruction(
                programId = PublicKey(apiInstruction.programId),
                data = apiInstruction.data
                    .map(Int::toByte)
                    .toByteArray(),
                keys = apiInstruction.keys.map { key ->
                    AccountMeta(
                        publicKey = PublicKey(key.pubkey),
                        signer = key.isSigner,
                        writable = key.isWritable
                    )
                },
            )
        }

        return Transaction(
            recentBlockhash = blockhash,
            instructions = instructions,
            feePayer = PublicKey(publicKey)
        )
    }

    private suspend fun generateInstructions(
        type: String,
        params: Map<String, Any>
    ): GenerateInstructionData {
        return api.generateInstruction(
            request = GenerateInstructionRequest(type = type, params = params)
        ).data ?: error("Failed to generate instructions")
    }
}