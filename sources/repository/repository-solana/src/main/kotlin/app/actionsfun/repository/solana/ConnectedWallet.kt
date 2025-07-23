package app.actionsfun.repository.solana

import kotlinx.serialization.Serializable

@Serializable
data class ConnectedWallet(
    val userId: String,
    val authToken: String,
    val publicKey: String,
    val accountLabel: String,
    val accounts: List<Account>,
) {

    @Serializable
    data class Account(
        val publicKey: String,
        val accountLabel: String,
    )

    companion object {
        val Empty = ConnectedWallet("", "", "", "", emptyList())
    }
}
