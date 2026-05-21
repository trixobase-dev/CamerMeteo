@file:Suppress("PropertyName")

package cm.trixobase.camermeteo.data.model

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

data class Temperature (
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int, //hPa
    val humidity: Int, //%
)
