package cm.trixobase.camermeteo.domain

import cm.trixobase.camermeteo.R

/*
 * Powered by Trixobase Enterprise on 25/05/26
 */

enum class NotificationWeather(val type: String, val title: Int, val content: Int) {

    RAIN_1(
        type = "rain",
        title = R.string.notification_rain_1_title,
        content = R.string.notification_rain_1_content
    ),



    SUN_1(
        type = "sun",
        title = R.string.notification_sun_1_title,
        content = R.string.notification_sun_1_content
    );

    override fun toString(): String {
        return "[name=$name, title=$title, content=$content]"
    }

}