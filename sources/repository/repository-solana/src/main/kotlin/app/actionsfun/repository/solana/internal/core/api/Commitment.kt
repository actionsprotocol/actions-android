package app.actionsfun.repository.solana.internal.core.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Commitment {
    @SerialName("finalized")
    Finalized,

    @SerialName("confirmed")
    Confirmed,

    @SerialName("processed")
    Processed;

    override fun toString(): String {
        return this.name.lowercase()
    }
}
