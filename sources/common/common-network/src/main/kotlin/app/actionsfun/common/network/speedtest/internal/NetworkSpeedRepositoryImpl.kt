package app.actionsfun.common.network.speedtest.internal

import app.actionsfun.common.network.speedtest.NetworkSpeedRepository
import app.actionsfun.common.network.speedtest.internal.api.SpeedtestApi
import app.actionsfun.common.network.speedtest.internal.api.model.Server
import app.actionsfun.common.network.speedtest.internal.api.model.SpeedTestServers
import app.actionsfun.common.network.speedtest.internal.download.HttpDownloadTest
import app.actionsfun.common.network.speedtest.internal.model.SpeedTestServer
import app.actionsfun.common.network.speedtest.internal.util.AllowAllHostNameVerifier
import app.actionsfun.common.network.speedtest.model.NetworkSpeed
import app.actionsfun.common.network.speedtest.internal.upload.HttpUploadTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory

internal class NetworkSpeedRepositoryImpl(
    private val api: SpeedtestApi,
    private val httpUploadTest: HttpUploadTest,
    private val httpDownloadTest: HttpDownloadTest,
) : NetworkSpeedRepository {

    override suspend fun getNetworkSpeed(): NetworkSpeed {
        return withContext(Dispatchers.IO) {
            val server = selectServer()
            server?.let { (downloadUrl, uploadUrl) ->
                val downloadSpeed = httpDownloadTest.execute(downloadUrl)
                val uploadSpeed = httpUploadTest.execute(uploadUrl)
                return@withContext NetworkSpeed(
                    downloadMbs = downloadSpeed.finalRate,
                    uploadMbs = uploadSpeed.finalRate
                )
            }

            NetworkSpeed(0.0, 0.0)
        }
    }

    private suspend fun selectServer(): SpeedTestServer? {
        return withContext(Dispatchers.IO) {
            runCatching { api.getServers() }
                .mapCatching { response -> SpeedTestServers.parseXml(response.string()) }
                .mapCatching { response ->
                    response.servers.serverList
                        .map(Server::url)
                        .map { url ->
                            url
                                .replace("http://", "https://")
                                .replace("/upload.php", "")
                        }
                        .map { server ->
                            SpeedTestServer(
                                upload = "$server/upload.php",
                                download = "$server/download",
                            )
                        }
                }.mapCatching { servers ->
                    servers.firstOrNull { server ->
                        testServerConnection(server)
                    }
                }
                .getOrNull()
        }
    }

    private suspend fun testServerConnection(server: SpeedTestServer): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(server.upload)
                    .openConnection() as HttpsURLConnection

                connection.apply {
                    doOutput = true
                    requestMethod = "POST"
                    setRequestProperty("Connection", "Keep-Alive")
                    setRequestProperty("Content-Type", "application/octet-stream")
                    connectTimeout = 5000
                    readTimeout = 5000
                    sslSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
                    hostnameVerifier = AllowAllHostNameVerifier
                }

                connection.connect()
                val responseCode = connection.responseCode
                connection.disconnect()

                responseCode in 200..299 || responseCode == 405
            } catch (e: Exception) {
                false
            }
        }
    }
}