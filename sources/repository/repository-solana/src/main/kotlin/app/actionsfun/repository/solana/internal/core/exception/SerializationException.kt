package app.actionsfun.repository.solana.internal.core.exception

import java.lang.RuntimeException

data class SerializationException(
    override val message: String,
) : RuntimeException(message)
