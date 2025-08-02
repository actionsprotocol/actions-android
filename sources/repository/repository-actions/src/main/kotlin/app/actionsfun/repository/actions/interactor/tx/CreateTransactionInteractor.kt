package app.actionsfun.repository.actions.interactor.tx

import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionData
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionRequest
import app.actionsfun.repository.actions.internal.api.model.InstructionData
import app.actionsfun.repository.solana.WalletRepository
import com.solana.publickey.SolanaPublicKey
import com.solana.transaction.Message
import com.solana.transaction.TransactionInstruction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

internal interface CreateTransactionInteractor {

    suspend fun createDepositToMarketTransaction(
        marketAddress: String,
        amountLamports: BigDecimal,
        option: Boolean,
    ): com.solana.transaction.Transaction

    suspend fun createClaimSolTransaction(
        marketAddress: String
    ): com.solana.transaction.Transaction
}

internal class CreateTransactionInteractorImpl(
    private val api: ActionsApi,
    private val walletRepository: WalletRepository,
) : CreateTransactionInteractor {

    override suspend fun createDepositToMarketTransaction(
        marketAddress: String,
        amountLamports: BigDecimal,
        option: Boolean
    ): com.solana.transaction.Transaction {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected."
            }

            val instructionData = generateInstructions(
                type = "makePrediction",
                params = mapOf(
                    "marketAddress" to marketAddress,
                    "option" to option,
                    "amountLamports" to amountLamports.longValueExact(),
                    "participantAddress" to wallet.publicKey,
                )
            )

            instructionData.instructions.buildTransaction()
        }
    }

    override suspend fun createClaimSolTransaction(
        marketAddress: String
    ): com.solana.transaction.Transaction {
        return withContext(Dispatchers.IO) {
            val wallet = walletRepository.getWallet()

            check(!wallet.isNone) {
                "Wallet is not connected."
            }

            val instructionData = generateInstructions(
                type = "claimWinnings",
                params = mapOf(
                    "marketAddress" to marketAddress,
                    "participantAddress" to wallet.publicKey,
                )
            )

            instructionData.instructions.buildTransaction()
        }
    }

    private suspend fun List<InstructionData>.buildTransaction(): com.solana.transaction.Transaction {
        val blockhash = walletRepository.getLatestBlockhash()
        val instructions = map { apiInstruction ->
            TransactionInstruction(
                programId = SolanaPublicKey.from(apiInstruction.programId),
                accounts = apiInstruction.keys.map { key ->
                    com.solana.transaction.AccountMeta(
                        publicKey = SolanaPublicKey.from(key.pubkey),
                        isSigner = key.isSigner,
                        isWritable = key.isWritable
                    )
                },
                data = apiInstruction.data
                    .map(Int::toByte)
                    .toByteArray()
            )
        }
        val message = Message.Builder()
            .also { builder ->
                instructions.forEach(builder::addInstruction)
            }
            .setRecentBlockhash(blockhash)
            .build()

        return com.solana.transaction.Transaction(message)
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