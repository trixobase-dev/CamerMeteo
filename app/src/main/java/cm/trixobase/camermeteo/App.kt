@file:Suppress("unused", "PropertyName")

package cm.trixobase.camermeteo

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.model.Sys
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.model.Wind
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.domain.MyWorker
import cm.trixobase.camermeteo.domain.NotificationWeather
import cm.trixobase.camermeteo.ui.view.MainActivity
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.domain.ApplicationObject
import cm.trixobase.library.common.utils.NotificationProcess
import cm.trixobase.library.common.utils.Utils
import java.util.Calendar
import java.util.concurrent.TimeUnit

/*
 * Powered by Trixobase Enterprise on 16/04/26
 */

class App : ApplicationObject() {

    override fun onCreate() {
        super.onCreate()
        NotificationProcess.builder(this)
            .setChannel(
                AttributeNames.CHANNEL_ID_WEATHER_SUN,
                getString(cm.trixobase.library.common.R.string.warning_sun))
            .setChannel(
                AttributeNames.CHANNEL_ID_WEATHER_RAIN,
                getString(cm.trixobase.library.common.R.string.warning_rain))

        val periodicWork = PeriodicWorkRequestBuilder<MyWorker>(
            repeatInterval = 20,
            repeatIntervalTimeUnit = TimeUnit.MINUTES).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("myWork",
            ExistingPeriodicWorkPolicy.REPLACE, periodicWork)
    }

    companion object {

        const val API_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/weather/"
        const val API_WEATHER_KEY_ID = "appid=79481cd4c6034c853f7e1d9e6315bed2"
        const val APP_URL_POLICY = "https://google.com?search=policy"
        const val APP_DATABASE_NAME = "db_camermeteo"

        fun getLocation(context: Context): Map<String, String> {
            return if (Utils.process.get(context, AttributeNames.KEY_APP_LOCALISATION_AUTO, false))
                 Utils.phone.getLocation(context)
            else {
                val city = getCity(context)
                mapOf("latitude" to city.lat, "longitude" to city.lon, "city" to city.display)
            }
        }

        fun getLanguage(context: Context): Language {
            val langue = Utils.process.get(context, AttributeNames.KEY_APP_LANGUAGE, Language.FRENCH.name)
            return Language.entries.filter { langue == it.name }[0]
        }

        fun getCity(context: Context): City {
            val city = Utils.process.get(context, AttributeNames.KEY_APP_CITY, City.YAOUNDE.name)
            return City.entries.filter { city == it.name }[0]
        }

        fun isDemo(context: Context): Boolean = Utils.process.get(context, AttributeNames.KEY_APP_DEMO_CONFIGURATION, false)

        fun localisationIsOn(context: Context): Boolean = Utils.process.get(context, AttributeNames.KEY_APP_LOCALISATION_AUTO, false)

        fun getWeatherDemo(): ApiResult {
            return ApiResult(
                name = "Cameroun",
                weather = listOf(
                    Weather(
                        id = 501,
                        icon = "10n",
                        main = "Rain",
                        description = "Pluie modérée"
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

        fun getCurrentDate(): String {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            return "$year-${compute(month)}-${compute(day)}"
        }

        fun getWeatherNotificationDemo(context: Context): Notification? {
            return if (isDemo(context))
             getWeatherNotification(context, getWeatherDemo()) else null
        }

        fun getWeatherNotification(context: Context, weather: ApiResult): Notification? {
            val id = weather.weather[0].id
            val description = weather.weather[0].main
            if (!description.contains("rain", true) || !description.contains("sun", true))
                return null

            val pendingIntent = PendingIntent.getActivity(context, 0,
                    Intent(context, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val rainIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_RAIN, true)
            val sunIsOn = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATION_SUN, true)

            return when {
                description.contains("rain", true) && rainIsOn -> {
                    val notification = getRandomNotification(context = context, type = "rain")
                    NotificationProcess.builder(context)
                        .setText(
                            tickerTitle = context.getString(cm.trixobase.library.common.R.string.warning_rain),
                            title = context.getString(notification.title),
                            content = context.getString(notification.content)
                        )
                        .setPictures(
                            logo = cm.trixobase.library.common.R.drawable.iv_icon_rain,
                            tickerPicture = R.drawable.iv_logo_ticker)
                        .build(
                            pendingIntent = pendingIntent,
                            channelId = AttributeNames.CHANNEL_ID_WEATHER_RAIN)
                }
                description.contains("sun", true) && sunIsOn -> {
                    val notification = getRandomNotification(context = context, type = "sun")
                    NotificationProcess.builder(context)
                        .setText(
                            tickerTitle = context.getString(cm.trixobase.library.common.R.string.warning_sun),
                            title = context.getString(notification.title),
                            content = context.getString(notification.content)
                        )
                        .setPictures(
                            logo = cm.trixobase.library.common.R.drawable.iv_icon_sun,
                            tickerPicture = R.drawable.iv_logo_ticker)
                        .build(
                            pendingIntent = pendingIntent,
                            channelId = AttributeNames.CHANNEL_ID_WEATHER_SUN)
                }
                else -> { null }
            }
        }

        fun getWeatherToShare(c: Context, apiResult: ApiResult): String {
            var location = getLocation(c)["city"]
            if (location.equals(c.getString(cm.trixobase.library.common.R.string.my_position)))
                location = apiResult.name
            return "CamerMétéo: " + c.getString(cm.trixobase.library.common.R.string.weather_day) +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.place)}: $location" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.description)}: ${apiResult.weather[0].description}" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.temperature)}: ${apiResult.main.temp} °C" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.rain)}: ${apiResult.main.humidity} %" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.wind)}: ${apiResult.wind.speed} m/s" +
                    "\n- ${c.getString(cm.trixobase.library.common.R.string.pressure)}: ${apiResult.main.pressure} hPa"
        }

        fun getPubHomeTop(): List<Int> = listOf(
            // Size 140 x 240px
            R.drawable.iv_pub_home_top_0,
            R.drawable.iv_pub_home_top_1
        )

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

        private fun getRandomNotification(context: Context, type: String): NotificationWeather {
            var i = 0
            var notifications = Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATIONS, "")
            val notification = NotificationWeather.entries.filter { type == it.type }.apply { i =(0..<this.size).random() }[i]
            notifications += if (notifications.isEmpty()) notification.name else ",${notification.name}"
            Utils.process.set(context, AttributeNames.KEY_APP_NOTIFICATIONS, notifications)
            return notification
        }

        private fun compute(time: Int): String {
            val t = time.toString()
            return if (t.length > 1) t else "0$t"
        }

    }

}