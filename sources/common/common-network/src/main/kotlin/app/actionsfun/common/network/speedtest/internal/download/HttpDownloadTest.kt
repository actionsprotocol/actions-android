package app.actionsfun.common.network.speedtest.internal.download

import app.actionsfun.common.network.speedtest.internal.model.DownloadResult
import kotlinx.coroutines.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

class HttpDownloadTest(
    private val timeout: Duration = 8.seconds,
    private val bufferSize: Int = 10240
) {
    suspend fun execute(serverUrl: String): DownloadResult {
        return withContext(Dispatchers.IO) {
            var totalDownloadedBytes = 0L
            val startTime = TimeSource.Monotonic.markNow()

            try {
                val fileUrl = "$serverUrl/$TestFile"

                val bytesDownloaded = downloadFile(fileUrl, startTime)
                totalDownloadedBytes += bytesDownloaded

                val finalElapsed = startTime.elapsedNow()

                DownloadResult(
                    finalRate = calculateRate(totalDownloadedBytes, finalElapsed),
                    totalBytes = totalDownloadedBytes,
                    elapsedTime = finalElapsed
                )
            } catch (e: Exception) {
                val finalElapsed = startTime.elapsedNow()
                DownloadResult(
                    finalRate = calculateRate(totalDownloadedBytes, finalElapsed),
                    totalBytes = totalDownloadedBytes,
                    elapsedTime = finalElapsed
                )
            }
        }
    }

    private suspend fun downloadFile(
        fileUrl: String,
        startTime: TimeMark
    ): Long = withContext(Dispatchers.IO) {
        var connection: HttpsURLConnection? = null
        var bytesDownloaded = 0L

        try {
            connection = createConnection(fileUrl)
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("Server returned HTTP ${connection.responseCode}")
            }

            connection.inputStream.use { input ->
                val buffer = ByteArray(bufferSize)

                while (isActive) {
                    val bytesRead = input.read(buffer)
                    if (bytesRead == -1) break

                    bytesDownloaded += bytesRead

                    if (startTime.elapsedNow() >= timeout) {
                        break
                    }
                }
            }

            bytesDownloaded
        } finally {
            connection?.disconnect()
        }
    }

    private fun createConnection(fileUrl: String): HttpsURLConnection {
        val url = URL(fileUrl)
        return (url.openConnection() as HttpsURLConnection).apply {
            sslSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
            hostnameVerifier = hostnameVerifier { _, _ -> true }
            connectTimeout = 5000
            readTimeout = 5000
        }
    }

    private fun calculateRate(bytes: Long, duration: Duration): Double {
        if (bytes <= 0 || duration == Duration.ZERO) return 0.0

        val seconds = duration.inWholeMilliseconds / 1000.0
        val megabits = (bytes * 8) / (1000.0 * 1000.0)

        return (megabits / seconds).round(2)
    }

    private fun Double.round(decimals: Int): Double {
        return try {
            BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()
        } catch (e: Exception) {
            0.0
        }
    }

    private fun hostnameVerifier(verify: (String?, javax.net.ssl.SSLSession?) -> Boolean): HostnameVerifier {
        return javax.net.ssl.HostnameVerifier { hostname, session -> verify(hostname, session) }
    }

    private companion object {
        private const val TestFile = "random4000x4000.jpg"
    }
}
