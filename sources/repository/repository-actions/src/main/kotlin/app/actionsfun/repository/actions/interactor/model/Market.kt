package app.actionsfun.repository.actions.interactor.model

import app.actionsfun.repository.actions.internal.api.model.MarketUiState
import java.time.OffsetDateTime

data class Market(
    val address: String,
    val title: String,
    val description: String,
    val image: String,
    val creatorTwitterUsername: String,
    val creatorTwitterImage: String,
    val createdAt: OffsetDateTime,
    val endsAt: OffsetDateTime,
    val volume: Float,
    val yesVolume: Float,
    val noVolume: Float,
    val replies: List<Reply>,
    val uiState: MarketUiState,
    val video: String? = null,
    val participants: List<Participant>,
) {

    val yesPercentage: Int
        get() = when {
            volume == 0f -> 50
            else -> (yesVolume / volume * 100).toInt()
        }

    val noPercentage: Int
        get() = when {
            volume == 0f -> 50
            else -> (noVolume / volume * 100).toInt()
        }
}
