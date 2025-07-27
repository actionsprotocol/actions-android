package app.actionsfun.repository.actions.internal.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetMarketsResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: List<SerializedMarket>? = null
)

@Serializable
internal data class SerializedMarket(
    val address: String,
    val name: String,
    val description: String,
    val creator: String,
    val expirySlot: String,
    val status: MarketStatus,
    val totalMarketSize: String,
    val yesAmount: String,
    val noAmount: String,
    val winningOption: Boolean? = null,
    val participantsRegistry: String,
    val createdSlot: String,
    val createdTimestamp: String,
    val expiryTimestamp: String,
    val finalizationTimestamp: String,
    val finalizationDeadline: String,
    val finalizationSlot: String? = null,
    val finalizedTimestamp: String? = null,
    val marketVaultPda: String,
    val creatorFeeAccountPda: String,
    val metadataUri: String,
    val bump: Int,
    val creatorTwitterUsername: String? = null,
    val creatorTwitterImage: String? = null,
    val creatorTwitterId: String? = null,
    val uiState: MarketUiState
)

@Serializable
enum class UIMarketState {
    @SerialName("Active")
    Active,
    @SerialName("Deciding")
    Deciding,
    @SerialName("CanceledByCreator")
    CanceledByCreator,
    @SerialName("AutoCanceled")
    AutoCanceled,
    @SerialName("FinalizedYes")
    FinalizedYes,
    @SerialName("FinalizedNo")
    FinalizedNo,
    @SerialName("FinalizedNoParticipantsYes")
    FinalizedNoParticipantsYes,
    @SerialName("FinalizedNoParticipantsNo")
    FinalizedNoParticipantsNo
}

@Serializable
internal enum class MarketStatus {
    @SerialName("Active")
    Active,
    @SerialName("Deciding")
    Deciding,
    @SerialName("Finalized")
    Finalized,
    @SerialName("FinalizedNoParticipants")
    FinalizedNoParticipants,
    @SerialName("AutoCanceled")
    AutoCanceled
}

@Serializable
enum class WinningOptionType {
    @SerialName("yes")
    Yes,
    @SerialName("no")
    No
}

@Serializable
data class MarketUiState(
    val state: UIMarketState,
    val winningOption: WinningOptionType? = null
)

@Serializable
internal data class MarketResponseItem(
    val publicKey: String,
    val account: SerializedMarket,
    val participants: List<SerializedParticipant>? = null
)

@Serializable
internal data class GetMarketResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: SerializedMarket? = null
)

@Serializable
internal data class MarketVaultData(
    @SerialName("poolId")
    val marketId: String,
    val vaultAddress: String
)

@Serializable
internal data class GetMarketVaultResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: MarketVaultData? = null
)


internal fun UIMarketState.isSettled(): Boolean {
    return when (this) {
        UIMarketState.FinalizedYes,
        UIMarketState.FinalizedNo,
        UIMarketState.FinalizedNoParticipantsYes,
        UIMarketState.FinalizedNoParticipantsNo,
        UIMarketState.CanceledByCreator,
        UIMarketState.AutoCanceled -> true
        else -> false
    }
}