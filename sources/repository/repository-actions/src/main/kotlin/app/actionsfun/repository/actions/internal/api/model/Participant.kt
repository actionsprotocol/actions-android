package app.actionsfun.repository.actions.internal.api.model

import app.actionsfun.common.network.serialization.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class GetParticipantsResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: List<SerializedParticipant>? = null
)

@Serializable
internal data class SerializedParticipant(
    val publicKey: String,
    val amount: String,
    val option: Boolean,
    val timestamp: Long
)

@Serializable
internal data class ParticipantInfo(
    val amount: String,
    val option: Boolean,
    val predictionTimestamp: Long
)