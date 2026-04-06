package cm.trixobase.camermeteo.ui.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector
import cm.trixobase.camermeteo.common.Utils
import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class UiDate {

    private constructor()

    lateinit var date: String
    lateinit var picture: ImageVector
    lateinit var temperature: String

    class Builder {

        internal constructor() {
            instance.date = "06/04/2026"
            instance.picture = Icons.Default.Face
            instance.temperature = "0° / 0°"
        }

        private val instance = UiDate()

        fun withDate(date: String): Builder {
            instance.date = date
            return this
        }

        fun withTemperature(temperature: Int): Builder {
            instance.temperature = "${temperature + 2}° / ${temperature - 2}°"
            when(temperature) {
                in 20..27
                -> instance.picture = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                in 28..33
                -> instance.picture = Icons.AutoMirrored.Filled.ExitToApp
                in 34..38
                -> instance.picture = Icons.AutoMirrored.Filled.KeyboardArrowRight
            }
            return this
        }

        fun build(): UiDate {
            return instance
        }


    }

    companion object {
        private fun builder(): Builder {
            return Builder()
        }

        fun getAll(): List<UiDate> {
            val dates = mutableListOf<UiDate>()
            var date = Utils.time.currentDate()
            repeat(6) {
                date.add(Calendar.DAY_OF_MONTH, 1)
                dates.add(builder()
                    .withDate(Utils.time.computeDateLittle(date))
                    .withTemperature((24..28).random())
                    .build())
            }
            return dates
        }

        private fun computeHour(hour: Int): String {
            return if (hour < 10)
                "0$hour:00"
            else "$hour:00"
        }
    }
}