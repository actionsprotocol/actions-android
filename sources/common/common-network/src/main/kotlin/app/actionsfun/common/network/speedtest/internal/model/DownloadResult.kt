package app.actionsfun.common.network.speedtest.internal.model

import kotlin.time.Duration

data class DownloadResult(
    val finalRate: Double,
    val totalBytes: Long,
    val elapsedTime: Duration
)