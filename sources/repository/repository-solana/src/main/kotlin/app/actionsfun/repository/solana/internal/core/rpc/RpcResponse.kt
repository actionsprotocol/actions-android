package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable

@Serializable
internal data class RpcResponse<T>(
    val result: T,
    val id: Long,
    val jsonrpc: String,
)
