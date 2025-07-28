package app.actionsfun.repository.actions.interactor.model

import java.time.OffsetDateTime

data class Participant(
    val publicKey: String,
    val amount: String,
    val option: Boolean,
    val timestamp: OffsetDateTime,
)
