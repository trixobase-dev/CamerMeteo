package cm.trixobase.camermeteo.data.datasource

import cm.trixobase.camermeteo.data.model.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

interface WeatherApi {

    @GET("/weather")
    suspend fun getWeather(): Response<WeatherResponse>

}