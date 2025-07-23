package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTokenAccountsResponse(
    val value: List<TokenAccountInfo>
)

@Serializable
data class TokenAccountInfo(
    val account: Account,
    @SerialName("pubkey")
    val publicKey: String
)

@Serializable
data class Account(
    val data: Data,
    val owner: String,
) {
    @Serializable
    data class Data(
        val parsed: Parsed,
        val program: String,
    ) {
        @Serializable
        data class Parsed(
            val info: Info,
            val type: String
        ) {
            @Serializable
            data class Info(
                val mint: String,
                val owner: String,
                val state: String,
                val tokenAmount: TokenAmount
            )
        }
    }
}
