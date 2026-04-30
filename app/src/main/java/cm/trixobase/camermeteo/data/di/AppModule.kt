@file:Suppress("ClassName")

package cm.trixobase.camermeteo.data.di

import cm.trixobase.camermeteo.data.datasource.WeatherApi
import cm.trixobase.camermeteo.domain.AttributeNames
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Powered by Trixobase Enterprise on 21/04/26
 */

object AppModule {

    enum class TEMPERATURE(val units: String, val unity: String) {
        CELSIUS(units = "metric", unity = AttributeNames.TEMPERATURE_UNITS_CELSIUS),
        FAHRENHEIT(units = "imperial", unity = AttributeNames.TEMPERATURE_UNITS_FAHRENHEIT),
        KELVIN(units = "standard", unity = AttributeNames.TEMPERATURE_UNITS_KELVIN);

        override fun toString(): String {
            return "[$name ($unity) => $units]"
        }
    }

    const val API_WEATHER_KEY_ID: String = "79481cd4c6034c853f7e1d9e6315bed2"
    const val API_WEATHER_BASE_URL: String = "https://api.openweathermap.org/data/3.0/onecall/"

    val weatherApi: WeatherApi = buildWeatherApi()

    private fun buildWeatherApi(): WeatherApi {
        return getRetrofit().create(WeatherApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}