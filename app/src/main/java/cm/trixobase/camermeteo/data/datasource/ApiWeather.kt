package cm.trixobase.camermeteo.data.datasource

import cm.trixobase.camermeteo.App
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

interface ApiWeather {

    @GET("?${App.API_WEATHER_KEY_ID}")
    suspend fun getWeather(
        @Query("lang") lang: String,
        @Query("units") units: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<ApiResult>

}