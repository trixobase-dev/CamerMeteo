package cm.trixobase.camermeteo.data.di

import cm.trixobase.camermeteo.data.datasource.ApiWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

object AppModule {

    const val API_WEATHER_KEY_ID: String = "79481cd4c6034c853f7e1d9e6315bed2"
    const val API_WEATHER_BASE_URL: String = "https://api.openweathermap.org/data/2.5/weather/"

    val weatherApi: ApiWeather = buildWeatherApi()

    private fun buildWeatherApi(): ApiWeather {
        return getRetrofit().create(ApiWeather::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}