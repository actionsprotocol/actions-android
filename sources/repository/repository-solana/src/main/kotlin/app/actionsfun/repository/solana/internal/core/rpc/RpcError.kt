package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable

@Serializable
internal data class RpcError(
    val code: Int,
    val message: String,
)
