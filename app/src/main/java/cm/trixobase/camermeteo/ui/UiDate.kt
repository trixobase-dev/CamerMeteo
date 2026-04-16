package cm.trixobase.camermeteo.ui

import cm.trixobase.camermeteo.common.Utils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class UiDate {

    private constructor()

    lateinit var dateToDisplay: String
    var picture: Int = 0
    lateinit var temperatureToDisplay: String

    class Builder {

        private var hour: Int = 0
        private var temperature: Int = 0



        internal constructor() {
            instance.dateToDisplay = "06/04/2026"
            instance.temperatureToDisplay = "0° / 0°"
        }

        private val instance = UiDate()

        fun withDate(date: Calendar): Builder {
            this.hour = date.get(Calendar.HOUR_OF_DAY)
            instance.dateToDisplay = Utils.time.computeDateLittle(date)
            return this
        }

        fun withTemperature(temperature: Int): UiDate {
            this.temperature = temperature
            instance.temperatureToDisplay = "${temperature + 2}° / ${temperature - 2}°"
            instance.picture = ApplicationManager.getWeatherPicture(temperature, hour)
            return instance
        }

    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        fun getAll(): List<UiDate> {
            val dates = mutableListOf<UiDate>()
            val date = Utils.time.currentDate()
            repeat(6) {
                date.add(Calendar.DAY_OF_MONTH, 1)
                dates.add(builder()
                    .withDate(date)
                    .withTemperature((24..28).random()))
            }
            return dates
        }
    }
}