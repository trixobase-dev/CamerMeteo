@file:Suppress("unused", "ClassName")

package cm.trixobase.camermeteo.common

import java.util.Calendar

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

object MyUtils {

    object time {

        fun currentDate(): Calendar {
            return Calendar.getInstance()
        }

        fun calendarByDate(date: String): Calendar {
            return stringToCalendar(date)
        }

        fun computeDateLittle(calendar: Calendar): String {
            return "${dayName(calendar).substring(0, 3)}. ${day(calendar)} ${monthName(calendar).lowercase().substring(0, 4)}."
        }

        private fun stringToCalendar(date: String): Calendar {
            return Calendar.Builder()
                .setDate(year(date), month(date) - 1, day(date)).build()
        }

        private fun year(date: String): Int {
            return date.substring(6, date.length).toInt()
        }

        private fun month(date: String): Int {
            return date.substring(3, 5).toInt()
        }

        private fun day(date: String): Int {
            return date.substring(0, 2).toInt()
        }

        private fun day(calendar: Calendar): Int {
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

        private fun dayName(calendar: Calendar): String {
            return when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> "Dimanche"
                2 -> "Lundi"
                3 -> "Mardi"
                4 -> "Mercredi"
                5 -> "Jeudi"
                6 -> "Vendredi"
                7 -> "Samedi"
                else -> "Error in ://Utils.time.dayName(calendar: Calendar)"
            }
        }

        private fun monthName(calendar: Calendar): String {
            return when(calendar.get(Calendar.MONTH)) {
                0 -> "Janvier"
                1 -> "Février"
                2 -> "Mars"
                3 -> "Avril"
                4 -> "Mai"
                5 -> "Juin"
                6 -> "Juillet"
                7 -> "Août"
                8 -> "Septembre"
                9 -> "Octobre"
                10 -> "Novembre"
                11 -> "Décembre"
                else -> "Error in ://Utils.time.monthName(calendar: Calendar)"
            }
        }

    }

}