@file:Suppress("PropertyName")

package cm.trixobase.camermeteo.data.model

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

data class Temperature (
    val temp: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String, //hPa
    val humidity: String, //%
)
