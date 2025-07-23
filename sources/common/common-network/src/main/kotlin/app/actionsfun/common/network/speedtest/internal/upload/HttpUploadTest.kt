package app.actionsfun.common.network.speedtest.internal.upload

import app.actionsfun.common.network.speedtest.internal.model.UploadResult
import app.actionsfun.common.network.speedtest.internal.model.UploadTestConfig
import kotlinx.coroutines.*
import java.io.DataOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL
import java.util.concurrent.atomic.AtomicLong
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory
import kotlin.time.*

class HttpUploadTest(
    private val config: UploadTestConfig = UploadTestConfig()
) {
    private val uploadedBytes = AtomicLong(0)

    suspend fun execute(uploadUrl: String): UploadResult = coroutineScope {
        uploadedBytes.set(0)
        val startTime = TimeSource.Monotonic.markNow()

        try {
            val workers = List(config.threadCount) { workerId ->
                async {
                    uploadWorker(
                        uploadUrl = uploadUrl,
                        startTime = startTime
                    )
                }
            }

            workers.awaitAll()

            val finalElapsed = startTime.elapsedNow()
            val totalBytes = uploadedBytes.get()

            UploadResult(
                finalRate = calculateRate(totalBytes, finalElapsed),
                totalBytes = totalBytes,
                elapsedTime = finalElapsed
            )
        } catch (e: Exception) {
            val finalElapsed = startTime.elapsedNow()
            val totalBytes = uploadedBytes.get()

            UploadResult(
                finalRate = calculateRate(totalBytes, finalElapsed),
                totalBytes = totalBytes,
                elapsedTime = finalElapsed
            )
        }
    }

    private suspend fun uploadWorker(
        uploadUrl: String,
        startTime: TimeMark
    ) = withContext(Dispatchers.IO) {
        val buffer = ByteArray(config.bufferSizeKB * 1024)
        buffer.fill((0..255).random().toByte())

        while (isActive && startTime.elapsedNow() < config.timeout) {
            var connection: HttpsURLConnection? = null

            try {
                connection = createConnection(uploadUrl)
                connection.connect()

                DataOutputStream(connection.outputStream).use { output ->
                    output.write(buffer)
                    output.flush()
                }

                val responseCode = connection.responseCode
                if (responseCode !in 200..299) {
                    throw IOException("Server returned HTTP $responseCode")
                }

                uploadedBytes.addAndGet(buffer.size.toLong())

            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        break
                    }
                    else -> throw e
                }
            } finally {
                connection?.disconnect()
            }

            delay(10)
        }
    }

    private fun createConnection(uploadUrl: String): HttpsURLConnection {
        val url = URL(uploadUrl)
        return (url.openConnection() as HttpsURLConnection).apply {
            doOutput = true
            requestMethod = "POST"
            setRequestProperty("Connection", "Keep-Alive")
            setRequestProperty("Content-Type", "application/octet-stream")
            connectTimeout = 5000
            readTimeout = 5000
            sslSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
            hostnameVerifier = hostnameVerifier { _, _ -> true }
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

    private fun hostnameVerifier(verify: (String?, javax.net.ssl.SSLSession?) -> Boolean) =
        javax.net.ssl.HostnameVerifier { hostname, session -> verify(hostname, session) }
}