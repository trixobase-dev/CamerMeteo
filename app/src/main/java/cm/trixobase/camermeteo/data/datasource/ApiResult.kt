package cm.trixobase.camermeteo.data.datasource

import cm.trixobase.camermeteo.data.model.Sys
import cm.trixobase.camermeteo.data.model.Temperature
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.model.Wind

/*
 * Powered by Trixobase Enterprise on 11/05/26
 */

data class ApiResult(
    val name: String,
    val weather: List<Weather>,
    val main: Temperature,
    val visibility: String, // en mètre
    val wind: Wind,
    val sys: Sys
)
