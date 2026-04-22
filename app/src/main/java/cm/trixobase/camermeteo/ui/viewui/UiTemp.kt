package cm.trixobase.camermeteo.ui.viewui

import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.library.common.MyUtils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 03/04/26
 */

class UiTemp {

    private constructor()

    lateinit var hourToDisplay: String
    var picture: Int = 0
    var temperature: Int = 20

    class Builder {

        internal constructor() {
            instance.hourToDisplay = "14:00"
            instance.temperature = 27
        }

        private val instance = UiTemp()
        var hour: Int = 0
        var hasRain: Boolean = false
        var hasVent: Boolean = false
        var hasSun: Boolean = true

        fun withHour(hour: Int): Builder {
            this.hour = if (hour > 23) 0 else hour
            instance.hourToDisplay = computeHourToDisplay(hour)
            return this
        }

        fun withTemperature(temperature: Int): Builder {
            instance.temperature = temperature
            return this
        }

        fun withPrecipitation(hasRain: Boolean, hasVent: Boolean, hasSun: Boolean): UiTemp {
            this.hasRain = hasRain
            this.hasVent = hasVent
            this.hasSun = hasSun
            instance.picture = ApplicationManager.Companion.getWeatherPicture(
                instance.temperature,
                hour,
                hasVent,
                hasRain,
                hasSun
            )
            return instance
        }

        private fun computeHourToDisplay(hour: Int): String {
            return if (hour < 10)
                "0$hour:00"
            else "$hour:00"
        }


    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        private fun randomBoolean(): Boolean {
            return ((1..10).random() % 2 == 0)
        }

        fun getAll(): List<UiTemp> {
            val cH = MyUtils.time.currentDate().get(Calendar.HOUR_OF_DAY)
            val h = if (cH > 10) 10 else cH
            val temps = mutableListOf<UiTemp>()
            for (i in h..23) {
                temps.add(
                    builder()
                        .withHour(i)
                        .withTemperature((25..38).random())
                        .withPrecipitation(
                            hasRain = randomBoolean(),
                            hasVent = randomBoolean(),
                            hasSun = randomBoolean()
                        )
                )
            }
            return temps
        }

    }

}