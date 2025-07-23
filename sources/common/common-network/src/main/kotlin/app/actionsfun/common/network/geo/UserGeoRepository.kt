package app.actionsfun.common.network.geo

import app.actionsfun.common.network.geo.model.UserGeo

interface UserGeoRepository {

    suspend fun getUserGeo(): UserGeo?
}