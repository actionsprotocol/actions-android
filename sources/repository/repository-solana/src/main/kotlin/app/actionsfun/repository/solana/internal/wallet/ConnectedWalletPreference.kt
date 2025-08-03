package app.actionsfun.repository.solana.internal.wallet

import androidx.annotation.Keep
import app.actionsfun.repository.preferences.Preference
import app.actionsfun.repository.solana.ConnectedWallet

@Keep
internal data object ConnectedWalletPreference : Preference<ConnectedWallet> {
    override val key: String = "Key:ConnectedWallet"
    override val defaultValue = ConnectedWallet.Empty
    override val serializer = ConnectedWallet.serializer()
}
