package cm.trixobase.camermeteo.data.di

import cm.trixobase.camermeteo.data.datasource.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

object AppModule {

    val weatherApi: WeatherApi = buildWeatherApi()
    const val API_BASE_URL: String = "https://api.openweather.com"
    const val API_KEY: String = "68dfb468bsd8fb1s6bg1ws68143"

    private fun buildWeatherApi(): WeatherApi {
        return getRetrofit().create(WeatherApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}