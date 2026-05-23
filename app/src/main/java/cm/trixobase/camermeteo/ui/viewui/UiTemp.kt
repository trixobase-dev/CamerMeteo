package cm.trixobase.camermeteo.ui.viewui

import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.Utils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 03/04/26
 */

class UiTemp {

    private constructor()

    lateinit var hourToDisplay: String
    var picture: Int = 0
    var temperature: Int = 0

    class Builder {

        internal constructor() {
            instance.hourToDisplay = "14:00"
            instance.temperature = 27
        }

        private val instance = UiTemp()
        var hour: Int = 0

        fun withHour(hour: Int): Builder {
            this.hour = if (hour > 23) 0 else hour
            instance.hourToDisplay = computeHourToDisplay(hour)
            return this
        }

        fun withTemperature(temperature: Int): UiTemp {
            instance.temperature = temperature
            instance.picture = ApplicationManager.getWeatherPicture(temperature, hour)
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

        fun getAll(min: Double, max: Double, unity: Temperature): List<UiTemp> {
            val cH = Utils.time.currentDate().get(Calendar.HOUR_OF_DAY)
            val h = if (cH > 10) 10 else cH
            val temps = mutableListOf<UiTemp>()
            for (i in h..23) {
                temps.add(
                    builder()
                        .withHour(i)
                        .withTemperature(getTemp(min, max, unity))
                )
            }
            return temps
        }

        private fun getTemp(min: Double, max: Double, temperature: Temperature): Int {
            val t: Int = (min.toInt()..max.toInt()).random()
            val r = ApplicationManager.convert(t.toDouble(), temperature)
            val v = r.toDouble().toInt()
            return v
        }

    }

}