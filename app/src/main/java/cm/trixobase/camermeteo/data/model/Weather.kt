package cm.trixobase.camermeteo.data.model

/*
 * Powered by Trixobase Enterprise on 27/04/26
 */

data class Weather(
    val lat: String,
    val lon: String,
    val units: String,
    val date: String,
    val humidity: Humidity,
    val pressure: Pressure,
    val precipitation: Precipitation,
    val temperature: Temperature,
    val wind: Wind
)