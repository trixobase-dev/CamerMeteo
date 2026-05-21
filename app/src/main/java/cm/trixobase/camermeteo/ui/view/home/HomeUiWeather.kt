package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 16/05/26
 */

class HomeUiWeather {

    private constructor(apiResult: ApiResult, temperature: Temperature) {
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

    fun getVisibility(): String = apiResult.visibility.toString() + " m"

    fun getDetails(): List<UiTemp> = UiTemp.getAll(apiResult.main.temp_min, apiResult.main.temp_max)

    fun getTemperatureMain(): String = getTemperature(apiResult.main.temp) + this.temperature.unity

    fun getTemperatureInterval(): String = getTemperature(apiResult.main.temp_min) + " / " + getTemperature(apiResult.main.temp_max)

    private fun getTemperature(temperature: Double): String  {
        val t = when(this.temperature) {
            Temperature.CELSIUS -> temperature
            Temperature.FAHRENHEIT -> Utils.maths.celsiusToFahrenheit(temperature)
            Temperature.KELVIN -> Utils.maths.celsiusToKelvin(temperature)
        }
        return Utils.maths.arroundDouble(t).toString()
    }

}