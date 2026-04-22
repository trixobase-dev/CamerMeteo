package cm.trixobase.camermeteo.data.datasource

import cm.trixobase.camermeteo.data.model.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

interface WeatherApi {

    @GET("/v1/current.json")
    suspend fun getWeather(@Query("key") apiKey: String, @Query("q") city: String): Response<Weather>

}