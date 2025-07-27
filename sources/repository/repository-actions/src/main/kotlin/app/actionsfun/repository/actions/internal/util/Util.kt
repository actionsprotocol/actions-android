package app.actionsfun.repository.actions.internal.util

import app.actionsfun.repository.actions.interactor.model.Participant
import app.actionsfun.repository.actions.interactor.model.Reply
import app.actionsfun.repository.actions.internal.api.model.ChatMessage
import app.actionsfun.repository.actions.internal.api.model.SerializedParticipant
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset


internal fun SerializedParticipant.toParticipant(): Participant {
    return Participant(
        publicKey = publicKey,
        amount = amount,
        option = option,
        timestamp = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC),
    )
}

internal fun ChatMessage.toReply(): Reply {
    return Reply(
        id = id,
        username = username,
        content = content,
        timestamp = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC),
        marketId = marketId
    )
}

internal fun String.parseTimestamp(): OffsetDateTime {
    return runCatching {
        when {
            toLongOrNull() != null -> Instant.ofEpochMilli(toLong()).atOffset(ZoneOffset.UTC)
            else -> OffsetDateTime.parse(this)
        }
    }.getOrDefault(OffsetDateTime.now())
}

internal fun String.lamportsToSOL(): Float {
    return runCatching {
        toBigDecimal().divide(1_000_000_000.toBigDecimal()).toFloat()
    }.getOrDefault(0f)
}