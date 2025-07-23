package app.actionsfun.common.network.ip

import android.content.Context
import java.net.Inet4Address
import java.net.NetworkInterface

interface UserIPProvider {

    fun provide(): String?
}

internal class UserIPProviderImpl : UserIPProvider {

    override fun provide(): String? {
        return runCatching {
            NetworkInterface.getNetworkInterfaces().asSequence()
                .flatMap { it.inetAddresses.asSequence() }
                .firstOrNull { it is Inet4Address && !it.isLoopbackAddress }
                ?.hostAddress
        }.getOrNull()
    }
}