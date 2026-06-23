package cm.trixobase.camermeteo.data.model

/*
 * Powered by Trixobase Enterprise on 27/04/26
 */

data class Weather(
    val id: Int,
    val icon: String,
    val main: String,
    val description: String
)