@file:Suppress("unused")

package cm.trixobase.camermeteo

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.model.Sys
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.model.Wind
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.domain.NotificationWeather
import cm.trixobase.camermeteo.service.MyService
import cm.trixobase.camermeteo.ui.view.MainActivity
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.NotificationProcess
import cm.trixobase.library.common.utils.Utils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 16/04/26
 */

class ApplicationManager : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationProcess.builder(this)
            .setChannel(
                AttributeNames.CHANNEL_ID_WEATHER_SUN,
                getString(cm.trixobase.library.common.R.string.warning_sun))
            .setChannel(
                AttributeNames.CHANNEL_ID_WEATHER_RAIN,
                getString(cm.trixobase.library.common.R.string.warning_rain))
    }

    companion object {

        fun startService(context: Context) {
            if (!MyService.isRunning)
                context.startService(Intent(context, MyService::class.java))
        }

        fun stopService(context: Context) {
            if (MyService.isRunning)
                context.stopService(Intent(context, MyService::class.java))
        }

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
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val time = Calendar.getInstance()
            time.timeInMillis = weather.dt

            val rainIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_RAIN, true)
            val sunIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_SUN, true)

            val description = weather.weather[0].main
            if (description.contains("rain", true) && rainIsOn) {
                val notification = getRandomNotification(context = context, type = "rain")
                time.add(Calendar.MINUTE, 3)
                NotificationProcess.builder(context)
                    .setText(
                        tickerTitle = getTitleTicker(context, notification.type),
                        title = context.getString(notification.title),
                        content = context.getString(notification.content)
                    )
                    .setPictures(R.drawable.iv_logo, R.drawable.iv_logo_ticker)
                    .setTime(time.timeInMillis)
                    .notify(AttributeNames.CHANNEL_ID_WEATHER_RAIN, pendingIntent)
            }

            if (description.contains("sun", true) && sunIsOn) {
                val notification = getRandomNotification(context = context, type = "sun")
                time.add(Calendar.MINUTE, 10)
                NotificationProcess.builder(context)
                    .setText(
                        tickerTitle = getTitleTicker(context, notification.type),
                        title = context.getString(notification.title),
                        content = context.getString(notification.content)
                    )
                    .setPictures(R.drawable.iv_logo, R.drawable.iv_logo_ticker)
                    .setTime(time.timeInMillis)
                    .notify(AttributeNames.CHANNEL_ID_WEATHER_SUN, pendingIntent)
            }

        }

        private fun getRandomNotification(context: Context, type: String): NotificationWeather {
            var i = 0
            var notifications = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATIONS, "")
            val notification = NotificationWeather.entries.filter { type == it.type }.apply { i =(0..<this.size).random() }[i]
            notifications += if (notifications.isEmpty()) notification.name else ",${notification.name}"
            Utils.process.set(context, AttributeNames.KEY_APP_NOTIFICATIONS, notifications)
            return notification
        }

        private fun getTitleTicker(context: Context, type: String): String {
            return if (type == "sun")
                context.getString(cm.trixobase.library.common.R.string.warning_sun)
            else context.getString(cm.trixobase.library.common.R.string.warning_rain)
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
                return R.drawable.iv_meteo_night_nuage
            return if (hasWind)
                R.drawable.iv_meteo_night_vent
            else R.drawable.iv_meteo_night_nuage_sun
        }

    }

}