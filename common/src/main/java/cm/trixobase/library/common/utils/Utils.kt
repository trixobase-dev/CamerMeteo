@file:Suppress("unused", "className", "constPropertyName", "spellCheckingInspection", "deprecation", "ObsoleteSdkInt")

package cm.trixobase.library.common.utils

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.net.toUri
import cm.trixobase.library.common.R
import cm.trixobase.library.common.ui.ExitActivity
import java.util.Calendar
import java.util.Locale


/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

object Utils {

    object time {

        fun currentDate(): Calendar {
            return Calendar.getInstance()
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

        private fun day(calendar: Calendar): Int {
            return calendar.get(Calendar.DAY_OF_MONTH)
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

    }

    object process {

        fun get(context: Context, key: String, defaultValue: String): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, defaultValue) ?: defaultValue
        }

        fun get(context: Context, key: String, defaultValue: Int): Int {
            return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue)
        }

        fun get(context: Context, key: String, defaultValue: Boolean): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(key, defaultValue)
        }

        fun set(context: Context, key: String, value: String) {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putString(key, value)
            }
        }

        fun set(context: Context, key: String, value: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putInt(key, value)
            }
        }

        fun set(context: Context, key: String, value: Boolean) {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putBoolean(key, value)
            }
        }

    }

    object phone {

        fun hasInternet(context: Context): Boolean {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (connectivity == null) {
                Log.d("NetworkCheck", "isNetworkAvailable: No")
                return false
            }
            val info = connectivity.allNetworkInfo
            for (i in info.indices) {
                if (info[i]!!.state == NetworkInfo.State.CONNECTED) {
                    Log.d("NetworkCheck", "isNetworkAvailable: Yes")
                    return true
                }
            }
            return false
        }

        fun stopApp(context: Context) {
            val intent = Intent(context, ExitActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NO_ANIMATION
                        or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            context.startActivity(intent)
        }

        fun shareApp(context: Context, shareMessage: String) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            intent.type = "text/plain"
            val shareIntent = Intent.createChooser(intent, context.getString(R.string.share_app))
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

        fun notify(context: Context, title: String, content: String, logo: Int) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notification = NotificationCompat.Builder(context, "trixobase_channel_id")
                    .setContentTitle(title).setContentText(content).setSmallIcon(logo).build()
                notificationManager.notify(1, notification)
            }
        }

        fun setLanguage(context: Context, language: String) {
             val myLocale = Locale(language)
            val config = Configuration(context.resources.configuration)

            Locale.setDefault(myLocale)

            if (isNouga())
                config.setLocale(myLocale)
            else config.locale = myLocale

            if (isJellyBeanMR1())
                context.createConfigurationContext(config)
            else context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        fun updateLanguage(wrapper: ContextThemeWrapper, language: String) {
            val myLocale = Locale(language)
            if (isJellyBeanMR1()) {
                val config = Configuration()
                config.setLocale(myLocale)
                //wrapper.applyOverrideConfiguration(config)
            }
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN)
        fun isJellyBean(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun isJellyBeanMR1(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
        fun isLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
        fun isNouga(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
        fun isTiramisu(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        }

    }

}