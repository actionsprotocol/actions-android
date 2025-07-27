package app.actionsfun.repository.actions.internal.api.model

import app.actionsfun.common.network.serialization.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
internal data class ChatMessage(
    val id: String,
    val publicKey: String,
    val username: String,
    val content: String,
    val timestamp: Long,
    val marketId: String,
    val participantInfo: ParticipantInfo? = null
)

@Serializable
internal data class GetChatMessagesResponse(
    val success: Boolean,
    val data: List<ChatMessage>,
    val marketId: String,
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null
)

@Serializable
internal data class AddChatMessageRequest(
    val publicKey: String,
    val username: String,
    val content: String,
)

@Serializable
internal data class AddChatMessageResponse(
    val success: Boolean,
    val data: ChatMessage? = null,
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null
)