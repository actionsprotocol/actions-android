package app.actionsfun.repository.actions.internal.api.model

import app.actionsfun.common.network.serialization.AnySerializer
import kotlinx.serialization.Serializable

@Serializable
internal data class InstructionKey(
    val pubkey: String,
    val isSigner: Boolean,
    val isWritable: Boolean
)

@Serializable
internal data class InstructionData(
    val programId: String,
    val keys: List<InstructionKey>,
    val data: List<Int>
)

@Serializable
internal data class GenerateInstructionData(
    val instructions: List<InstructionData>,
    val type: String
)

@Serializable
internal data class GenerateInstructionResponse(
    val success: Boolean,
    val data: GenerateInstructionData? = null,
    val error: String? = null,
    val message: String? = null
)

@Serializable
internal data class GenerateInstructionRequest(
    val type: String,
    val params: Map<String, @Serializable(with = AnySerializer::class) Any?>
)

@Serializable
internal data class DepositInstructionParams(
    val marketAddress: String,
    val option: Boolean,
    val amountLamports: String,
    val participantAddress: String
)

@Serializable
internal data class ClaimSolInstructionParams(
    val marketAddress: String,
    val participantAddress: String
)

@Serializable
internal data class CreateMarketInstructionParams(
    val marketName: String,
    val marketDescription: String,
    val expirySlot: String,
    val creatorAddress: String,
    val metadataUri: String,
    val finalizationDeadline: String
)

@Serializable
internal data class FinishMarketInstructionParams(
    val marketAddress: String,
    val winningOption: Boolean? = null,
    val creatorAddress: String
)

@Serializable
internal data class CreatorClaimFeesInstructionParams(
    val marketAddress: String,
    val creatorAddress: String
)