package cm.trixobase.camermeteo.ui.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector

/*
 * Powered by Trixobase Enterprise on 03/04/26
 */

class UiTemp {

    private constructor()

    lateinit var hour: String
    lateinit var picture: ImageVector
    var temperature: Int = 20

    class Builder {

        internal constructor() {
            instance.hour = "14:00"
            instance.picture = Icons.Default.Face
            instance.temperature = 37
        }

        private val instance = UiTemp()

        fun withHour(hour: String): Builder {
            instance.hour = hour
            return this
        }

        fun withTemperature(temperature: Int): Builder {
            instance.temperature = temperature
            when(instance.temperature) {
                in 25..27
                -> instance.picture = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                in 28..33
                -> instance.picture = Icons.AutoMirrored.Filled.ExitToApp
                in 34..38
                -> instance.picture = Icons.AutoMirrored.Filled.KeyboardArrowRight
            }
            return this
        }

        fun build(): UiTemp {
            return instance
        }


    }

    companion object {
        private fun builder(): Builder {
            return Builder()
        }

        fun getAll(): List<UiTemp> {
            val temps = mutableListOf<UiTemp>()
            for (i in 0..23) {
                temps.add(builder()
                    .withHour(computeHour(i))
                    .withTemperature((25..38).random())
                    .build())
            }
            return temps
        }

        private fun computeHour(hour: Int): String {
            return if (hour < 10)
                "0$hour:00"
            else "$hour:00"
        }
    }
}