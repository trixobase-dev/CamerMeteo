@file:Suppress("unused", "className", "constPropertyName", "spellCheckingInspection", "deprecation", "ObsoleteSdkInt")

package cm.trixobase.library.common.utils

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.core.os.LocaleListCompat
import cm.trixobase.library.common.R
import cm.trixobase.library.common.ui.domain.ExitActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URLEncoder
import java.util.Calendar
import kotlin.io.encoding.Base64


/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

object Utils {

    const val Trixobase_Phone_Number = "+237686820828"
    const val Trixobase_Web_Site = "https://trixobase.com"

    object maths {

        fun arroundDouble(value: Double): String =
            BigDecimal(value).setScale(1, RoundingMode.HALF_UP).toString()

        fun celsiusToFahrenheit(temperature: Double): Double = (temperature * (9 / 5)) + 32

        fun celsiusToKelvin(temperature: Double): Double = temperature + 273.15

    }

    object time {

        fun currentDate(): Calendar {
            return Calendar.getInstance()
        }

        fun getCurrentDate(): String {
            val c = Calendar.getInstance()
            val date = "${format(day(c))}/${format(month(c))}/${year(c)}"
            return date
        }

        fun getCurrentHour(): String {
            val c = Calendar.getInstance()
            val time = "${format(hour(c))}:${format(minute(c))}"
            return time
        }

        fun calendarByDate(date: String): Calendar {
            return stringToCalendar(date)
        }

        fun computeDateLittle(calendar: Calendar): String {
            return "${dayName(calendar).substring(0, 3)}. ${day(calendar)} ${
                monthNameLittle(
                    calendar
                ).lowercase()
            }"
        }

        private fun stringToCalendar(date: String): Calendar {
            return Calendar.Builder().setDate(year(date), month(date) - 1, day(date)).build()
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

        private fun year(calendar: Calendar): Int {
            return calendar.get(Calendar.YEAR)
        }

        private fun month(calendar: Calendar): Int {
            return calendar.get(Calendar.MONTH) + 1
        }

        private fun day(calendar: Calendar): Int {
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

        private fun hour(calendar: Calendar): Int {
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

        private fun minute(calendar: Calendar): Int {
            return calendar.get(Calendar.MINUTE)
        }

        private fun dayName(calendar: Calendar): String {
            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
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
            return when (calendar.get(Calendar.MONTH)) {
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

        private fun monthNameLittle(calendar: Calendar): String {
            return when (calendar.get(Calendar.MONTH)) {
                0 -> "Jan."
                1 -> "Févr."
                2 -> "Mars."
                3 -> "Avril"
                4 -> "Mai"
                5 -> "Juin"
                6 -> "Juil."
                7 -> "Août"
                8 -> "Sept."
                9 -> "Oct."
                10 -> "Nov."
                11 -> "Déc."
                else -> "Error in ://Utils.time.monthName(calendar: Calendar)"
            }
        }

        private fun format(time: Int): String {
            val t = time.toString()
            return if (t.length > 1) t else "0$t"
        }

    }

    object process {

        fun encrypt(world: String): String = Base64.encode(source = world.toByteArray())

        fun decrypt(worldCrypted: String): String = String(Base64.decode(source = worldCrypted))

        fun get(context: Context, key: String, defaultValue: String): String = sharedPreferences(context)
                .getString(key, defaultValue) ?: defaultValue

        fun get(context: Context, key: String, defaultValue: Int): Int = sharedPreferences(context)
                .getInt(key, defaultValue)

        fun get(context: Context, key: String, defaultValue: Boolean): Boolean = sharedPreferences(context)
                .getBoolean(key, defaultValue)

        fun set(context: Context, key: String, value: String) {
            sharedPreferences(context).edit {
                putString(key, value)
            }
        }

        fun set(context: Context, key: String, value: Int) {
            sharedPreferences(context).edit {
                putInt(key, value)
            }
        }

        fun set(context: Context, key: String, value: Boolean) {
            sharedPreferences(context).edit {
                putBoolean(key, value)
            }
        }

        private fun sharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)!!

    }

    object phone {

        fun hasInternet(context: Context): Boolean {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (connectivity == null) {
                Log.d("NetworkCheck", "No available network")
                return false
            }
            val info = connectivity.allNetworkInfo
            for (i in info.indices) {
                if (info[i]!!.state == NetworkInfo.State.CONNECTED) {
                    Log.d("NetworkCheck", "Available network: ${info[i].typeName}")
                    //val ipProcess: Process = Thread.currentThread().exec("/system/bin/ping -c 1 8.8.8.8")
                    //val exitValue = ipProcess.waitFor()
                    //ipProcess.destroy()
                    //val exitValue = 0
                    //Runnable {}.run(exec)

                    return true
                }
            }
            return false
        }

        fun launchCall(context: Context, phoneNumber: String = Trixobase_Phone_Number) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:$phoneNumber".toUri()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun openBrowser(context: Context, url: String = Trixobase_Web_Site) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun sendMessageWhatsApp(context: Context, phoneNumber: String = Trixobase_Phone_Number, message: String = "From trixobase\'s application..") {
            val number = PhoneNumberUtils.stripSeparators(phoneNumber)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.`package` = "com.whatsapp"
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET or Intent.FLAG_ACTIVITY_NEW_TASK
            //intent.setComponent(ComponentName("com.whatsapp", "com.whatsapp.Conversation"))
            intent.putExtra("jid", "$number@s.whatsapp.net")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.putExtra(Intent.EXTRA_PHONE_NUMBER, number)

            try {
                val pm = context.packageManager
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                context.startActivity(intent)
            } catch (e: Exception) {
                val text = URLEncoder.encode(message, "UTF-8")
                val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$text"
                openBrowser(context, url)
            }
        }

        fun shareText(context: Context, text: String) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            val shareIntent = Intent.createChooser(intent, context.getString(R.string.share))
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
        }

        fun rateApp(context: Context) {
            try {
                val uri = "market://details?id=${context.packageName}/".toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                val uri = "https://play.google.com/?id=${context.packageName}/".toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

        fun stopApp(context: Context) {
            val intent = Intent(context, ExitActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
            context.startActivity(intent)
        }

        fun setLanguage(context: Context, language: String) {
            if (isTiramisu()) {
                val localManager = context.getSystemService(LocaleManager::class.java)
                localManager.applicationLocales = if (language == "fr")
                    LocaleList.getEmptyLocaleList() else LocaleList.forLanguageTags(language)
            } else {
                val localeList = if (language == "fr")
                    LocaleListCompat.getEmptyLocaleList() else LocaleListCompat.forLanguageTags(
                    language
                )
                AppCompatDelegate.setApplicationLocales(localeList)
            }
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN)
        fun isJellyBean() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun isJellyBeanMR1() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
        fun isLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
        fun isNouga() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
        fun isTiramisu() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    }

}