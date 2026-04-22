@file:Suppress("unused")

package cm.trixobase.camermeteo

import android.app.Application

/*
 * Powered by Trixobase Enterprise on 16/04/26
 */

class ApplicationManager: Application() {

    companion object {
        fun getWeatherPicture(temperature: Int, hour: Int): Int {
            return if (hour < 18) lightPictures(temperature) else nightPictures(temperature)

        }
        fun getWeatherPicture(temperature: Int, hour: Int, hasVent: Boolean, hasRain: Boolean, hasSun: Boolean): Int {
            return if (hour < 18) lightPictures(temperature, hasVent, hasRain, hasSun) else nightPictures(temperature, hasVent, hasRain, hasSun)

        }

        private fun nightPictures(temperature: Int, hasVent: Boolean, hasRain: Boolean, hasSun: Boolean): Int {
            return when(temperature) {
                in 20..23
                    -> R.drawable.iv_meteo_night_rain_vent
                in 24..27
                    -> R.drawable.iv_meteo_night_rain
                in 28..33
                    -> R.drawable.iv_meteo_night_vent
                in 34..38
                    -> R.drawable.iv_meteo_night_nuage_sun
                else -> R.drawable.iv_meteo_night_rain_little
            }
        }

        private fun lightPictures(temperature: Int, hasVent: Boolean, hasRain: Boolean, hasSun: Boolean): Int {
            return when(temperature) {
                in 20..23
                    -> R.drawable.iv_meteo_light_rain_vent
                in 24..27
                    -> R.drawable.iv_meteo_light_rain
                in 28..33
                    -> R.drawable.iv_meteo_light_vent
                in 34..38
                    -> R.drawable.iv_meteo_light_nuage_sun
                else -> R.drawable.iv_meteo_light_rain_little
            }
        }

        private fun nightPictures(temperature: Int): Int {
            return when(temperature) {
                in 20..23
                    -> R.drawable.iv_meteo_night_rain_vent
                in 24..27
                    -> R.drawable.iv_meteo_night_rain
                in 28..33
                    -> R.drawable.iv_meteo_night_vent
                in 34..38
                    -> R.drawable.iv_meteo_night_nuage_sun
                else -> R.drawable.iv_meteo_night_rain_little
            }
        }

        private fun lightPictures(temperature: Int): Int {
            return when(temperature) {
                in 20..23
                    -> R.drawable.iv_meteo_light_rain_vent
                in 24..27
                    -> R.drawable.iv_meteo_light_rain
                in 28..33
                    -> R.drawable.iv_meteo_light_vent
                in 34..38
                    -> R.drawable.iv_meteo_light_nuage_sun
                else -> R.drawable.iv_meteo_light_rain_little
            }
        }
    }
}