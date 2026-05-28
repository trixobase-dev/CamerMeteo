package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.library.common.constants.Temperature

/*
 * Powered by Trixobase Enterprise on 16/05/26
 */

class HomeUiWeather {

    constructor(apiResult: ApiResult, temperature: Temperature) {
        this.apiResult = apiResult
        this.temperature = temperature
    }

    var apiResult: ApiResult
    var temperature: Temperature

    class Builder {

        internal constructor(apiResult: ApiResult, temperature: Temperature) {
            this.instance = HomeUiWeather(apiResult, temperature)
        }
        
        var instance: HomeUiWeather

        fun build(): HomeUiWeather {
            return instance
        }


    }

    companion object {
        fun builder(apiResult: ApiResult, temperature: Temperature): Builder = Builder(apiResult, temperature)
        
    }
    
    fun getMainPicture(): Int = ApplicationManager.getWeatherPicture(apiResult)

    fun getDescription(): String = apiResult.weather[0].description

    fun getHumidity(): String = apiResult.main.humidity.toString() + " %"

    fun getWind(): String = apiResult.wind.speed.toString() + " m/s"

    fun getPressure(): String = apiResult.main.pressure.toString() + " hPa"

    fun getVisibility(): String = getVisibility(apiResult.visibility)

    fun getDetails(): List<HomeUiWeatherHour> = HomeUiWeatherHour.getAll(apiResult.main.temp_min, apiResult.main.temp_max, temperature)

    fun getTemperatureMain(): String = getTemperature(apiResult.main.temp) + "°"

    fun getUnity(): String = this.temperature.display

    fun getTemperatureInterval(): String = getTemperature(apiResult.main.temp_min) + "° / " + getTemperature(apiResult.main.temp_max) + "°"

    private fun getVisibility(value: Int): String  {
        return if (value >= 1000) "${value/1000} Km" else "$value m"
    }

    private fun getTemperature(temperature: Double): String  {
        return ApplicationManager.convert(temperature, this.temperature)
    }

}