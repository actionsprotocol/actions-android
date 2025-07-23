package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable
import app.actionsfun.repository.solana.internal.core.rpc.RpcError

@Serializable
internal data class RpcErrorResponse(
    val error: RpcError,
    val id: Long,
    val jsonrpc: String,
)
