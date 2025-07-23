package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal data class Balance(
    @Serializable(with = BigIntegerSerializer::class)
    val value: BigInteger,
)
