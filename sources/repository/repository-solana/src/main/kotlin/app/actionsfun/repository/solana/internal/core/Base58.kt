package app.actionsfun.repository.solana.internal.core

import app.actionsfun.repository.solana.internal.core.utilities.Base58 as Base58Encoding

object Base58 {
    @JvmStatic
    fun encode(input: ByteArray): String = Base58Encoding.encode(input)

    @JvmStatic
    fun decode(input: String): ByteArray = Base58Encoding.decode(input)
}
