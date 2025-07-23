package app.actionsfun.repository.solana.internal.core.exception

import java.lang.RuntimeException

data class RpcException(
    val code: Int,
    override val message: String,
    val rawResponse: String,
) : RuntimeException(message)
