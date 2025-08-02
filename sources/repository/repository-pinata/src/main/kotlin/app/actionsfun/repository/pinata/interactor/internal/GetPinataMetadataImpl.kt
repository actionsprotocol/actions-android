package app.actionsfun.repository.pinata.interactor.internal

import app.actionsfun.common.network.backendurl.BackendUrl
import app.actionsfun.repository.pinata.interactor.GetPinataMetadata
import app.actionsfun.repository.pinata.interactor.model.PinataMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

internal class GetPinataMetadataImpl(
    private val okhttp: OkHttpClient,
    private val json: Json,
) : GetPinataMetadata {

    override suspend fun get(url: String): PinataMetadata {
        return withContext(Dispatchers.IO) {
            val response = okhttp.newCall(
                Request.Builder()
                    .url(getHttpUrl(url))
                    .get()
                    .build()
            ).execute()

            json.decodeFromString(response.body?.string().orEmpty())
        }
    }

    private fun getHttpUrl(url: String): String {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url
        }

        if (url.startsWith("ipfs://")) {
            val hash = url.replace("ipfs://", "")
            return "$PinataBaseUrl/ipfs/$hash"
        }

        if (url.startsWith("Qm") || url.startsWith("baf")) {
            return "$PinataBaseUrl/ipfs/$url"
        }

        return url
    }

    private companion object {

        private val PinataBaseUrl = BackendUrl.Environmental.pinataBaseUrl
    }
}