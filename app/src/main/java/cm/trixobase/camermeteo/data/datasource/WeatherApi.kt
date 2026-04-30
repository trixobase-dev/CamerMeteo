package cm.trixobase.camermeteo.data.datasource

import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

interface WeatherApi {

    @GET("day_summary?appid=${AppModule.API_WEATHER_KEY_ID}")
    suspend fun getWeather(
        @Query("units") units: String,
        @Query("date") date: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<Weather>

}