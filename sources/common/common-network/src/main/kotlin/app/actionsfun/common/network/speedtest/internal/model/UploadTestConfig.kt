package app.actionsfun.common.network.speedtest.internal.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class UploadTestConfig(
    val threadCount: Int = 4,
    val bufferSizeKB: Int = 150,
    val timeout: Duration = 8.seconds
)