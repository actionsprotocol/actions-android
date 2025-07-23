package app.actionsfun.common.network.speedtest

import app.actionsfun.common.network.speedtest.model.NetworkSpeed

interface NetworkSpeedRepository {

    suspend fun getNetworkSpeed(): NetworkSpeed
}
