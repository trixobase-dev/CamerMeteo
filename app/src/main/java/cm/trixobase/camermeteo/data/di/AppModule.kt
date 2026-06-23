package cm.trixobase.camermeteo.data.di

import cm.trixobase.camermeteo.App
import cm.trixobase.camermeteo.data.datasource.ApiWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

object AppModule {

    val weatherApi: ApiWeather = buildWeatherApi()

    private fun buildWeatherApi(): ApiWeather {
        return getRetrofit().create(ApiWeather::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(App.API_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}