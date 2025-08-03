package app.actionsfun.repository.solana

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ConnectedWallet(
    val userId: String,
    val authToken: String,
    val publicKey: String,
    val accountLabel: String,
    val accounts: List<Account>,
) {

    val isNone: Boolean
        get() = this == Empty

    @Keep
    @Serializable
    data class Account(
        val publicKey: String,
        val accountLabel: String,
    )

    companion object {
        val Empty = ConnectedWallet("", "", "", "", emptyList())
    }
}
