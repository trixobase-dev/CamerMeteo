package cm.trixobase.camermeteo

import android.app.Application
import android.content.Context
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.model.Sys
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.model.Wind
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.MyNotification
import cm.trixobase.library.common.utils.Utils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 16/04/26
 */

class ApplicationManager : Application() {

    override fun onCreate() {
        super.onCreate()
        MyNotification.builder(this)
            .buildChannel(
                AttributeNames.CHANNEL_ID_WEATHER_SUN,
                AttributeNames.CHANNEL_WEATHER_SUN)
            .buildChannel(
                AttributeNames.CHANNEL_ID_WEATHER_RAIN,
                AttributeNames.CHANNEL_WEATHER_RAIN)
    }

    companion object {

        fun getWeatherDemo(): ApiResult {
            return ApiResult(
                name = "Cameroun",
                weather = listOf(
                    Weather(
                        main = "Rain",
                        description = "Temps nuageux"
                    )
                ),
                main = cm.trixobase.camermeteo.data.model.Temperature(
                    temp = 32.5,
                    temp_min = 28.0,
                    temp_max = 34.7,
                    pressure = 1021,
                    humidity = 60
                ),
                visibility = 2000,
                dt = System.currentTimeMillis(),
                wind = Wind(
                    speed = 4.09,
                    deg = 124,
                    gust = 3.47
                ),
                sys = Sys(
                    country = "CM",
                    sunrise = 1726636384,
                    sunset = 1726680975
                )
            )
        }

        fun setWeatherNotification(context: Context, weather: ApiResult) {
            val time = Calendar.getInstance()
            time.timeInMillis = weather.dt
            val rainIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_RAIN, true)
            val sunIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_SUN, true)

            time.add(Calendar.SECOND, 5)
            val description = weather.weather[0].main
            if (description.contains("rain", true) && rainIsOn)
                MyNotification.builder(context)
                    .setText("Forte pluie et vents forts", "N\'oublie pas ton parapluie molah.")
                    .setTime(time.timeInMillis)
                    .build(AttributeNames.CHANNEL_ID_WEATHER_RAIN)

            time.add(Calendar.MINUTE, 2)
            if (description.contains("sun", true) && sunIsOn)
                MyNotification.builder(context)
                    .setText("Ciel dégagé", "Comby, n\'oublie surtout pas de beaucoup boire d\'eau. Va faite très chaud aujourd\'hui.")
                    .setTime(time.timeInMillis)
                    .build(AttributeNames.CHANNEL_ID_WEATHER_SUN)

        }

        fun getWeatherToShare(c: Context, apiResult: ApiResult): String {
            return "CamerMétéo: " + c.getString(cm.trixobase.library.common.R.string.weather_day) +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.place)}: ${apiResult.name}" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.temperature)}: ${apiResult.main.temp} °C" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.rain)}: ${apiResult.main.humidity} %" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.wind)}: ${apiResult.wind.speed} m/s" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.pressure)}: ${apiResult.main.pressure} hPa"
        }

        fun convert(temperature: Double, unity: Temperature): String {
            val value = when(unity) {
                Temperature.CELSIUS -> temperature
                Temperature.FAHRENHEIT -> Utils.maths.celsiusToFahrenheit(temperature)
                Temperature.KELVIN -> Utils.maths.celsiusToKelvin(temperature)
            }
            return Utils.maths.arroundDouble(value)
        }

        fun getWeatherPicture(temperature: Int, hour: Int): Int {
            return if (hour in 6..18) {
                when(temperature) {
                    in 0..24 -> R.drawable.iv_meteo_light_rain_little
                    in 25..28 -> R.drawable.iv_meteo_light_rain_vent
                    in 28..30 -> R.drawable.iv_meteo_light_nuage_sun
                    in 30..40 -> R.drawable.iv_meteo_sun_nuage
                    else -> R.drawable.iv_meteo_sun
                }
            } else {
                when(temperature) {
                    in 0..24 -> R.drawable.iv_meteo_night_rain_little
                    in 25..28 -> R.drawable.iv_meteo_night_rain
                    in 28..30 -> R.drawable.iv_meteo_night_vent
                    else -> R.drawable.iv_meteo_night_nuage_sun
                }
            }
        }

        fun getWeatherPicture(weather: ApiResult): Int {
            val time = Calendar.getInstance()
            time.timeInMillis = weather.dt
            val hour = time.get(Calendar.HOUR_OF_DAY)
            return if (hour in 6..18) lightWeather(weather) else nightWeather(weather)
        }

        private fun lightWeather(weather: ApiResult): Int {
            val description = weather.weather[0].main
            if (description.contains("rain", true))
                return lightRain(weather.main.humidity, weather.wind.speed > 5)
            return if (description.contains("cloud", true))
                lightCloud(weather.main.humidity, weather.wind.speed > 5)
            else
                lightSun(weather.main.humidity, weather.wind.speed > 5)
        }

        private fun lightSun(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_sun_nuage
            return if (hasWind)
                    R.drawable.iv_meteo_light_nuage_sun
                else R.drawable.iv_meteo_sun
        }

        private fun lightCloud(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_light_nuage_sun
            return if (hasWind)
                    R.drawable.iv_meteo_light_rain_little
                else R.drawable.iv_meteo_light_vent
        }

        private fun lightRain(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_light_rain_little
            return if (hasWind)
                    R.drawable.iv_meteo_light_rain_vent
                else R.drawable.iv_meteo_light_rain
        }

        private fun nightWeather(weather: ApiResult): Int {
            val description = weather.weather[0].main
            if (description.contains("rain", true))
                return nightRain(weather.main.humidity, weather.wind.speed > 5)
            return if (description.contains("cloud", true))
                nightCloud(weather.main.humidity, weather.wind.speed > 5)
            else
                nightEvening(weather.main.humidity, weather.wind.speed > 5)
        }

        private fun nightRain(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_night_rain_little
            return if (hasWind)
                R.drawable.iv_meteo_night_rain_vent
            else R.drawable.iv_meteo_night_rain
        }

        private fun nightCloud(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_night_nuage_sun
            return if (hasWind)
                R.drawable.iv_meteo_night_rain_little
            else R.drawable.iv_meteo_night_vent
        }

        private fun nightEvening(humidity: Int, hasWind: Boolean): Int {
            if (humidity < 30)
                return R.drawable.iv_meteo_night_nuage_sun
            return if (hasWind)
                R.drawable.iv_meteo_night_vent
            else R.drawable.iv_meteo_night_nuage_sun
        }

    }

}