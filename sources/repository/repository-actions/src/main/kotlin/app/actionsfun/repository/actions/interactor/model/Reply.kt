package app.actionsfun.repository.actions.interactor.model

import java.time.OffsetDateTime

data class Reply(
    val id: String,
    val username: String,
    val content: String,
    val timestamp: OffsetDateTime,
    val marketId: String,
)
