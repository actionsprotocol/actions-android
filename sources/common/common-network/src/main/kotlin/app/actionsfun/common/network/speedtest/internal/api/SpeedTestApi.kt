package app.actionsfun.common.network.speedtest.internal.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface SpeedtestApi {

    @GET("speedtest-servers-static.php")
    suspend fun getServers(): ResponseBody

    @GET("speedtest-config.php")
    suspend fun getConfig(): ResponseBody
}
