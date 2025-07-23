package app.actionsfun.common.network.geo.internal

import app.actionsfun.common.network.geo.UserGeoRepository
import app.actionsfun.common.network.geo.model.UserGeo
import app.actionsfun.common.network.speedtest.internal.api.SpeedtestApi
import app.actionsfun.common.network.speedtest.internal.api.model.SpeedtestConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserGeoRepositoryImpl(
    private val speedtestApi: SpeedtestApi,
) : UserGeoRepository {

    override suspend fun getUserGeo(): UserGeo? {
        return withContext(Dispatchers.IO) {
            runCatching { speedtestApi.getConfig() }
                .mapCatching { response -> SpeedtestConfig.parseXml(response.string()) }
                .mapCatching { config ->
                    UserGeo(
                        country = config.client.country,
                        lat = config.client.lat.toDoubleOrNull(),
                        lng = config.client.lon.toDoubleOrNull(),
                    )
                }
                .getOrNull()
        }
    }
}
