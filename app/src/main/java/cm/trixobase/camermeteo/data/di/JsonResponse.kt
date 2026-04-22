package cm.trixobase.camermeteo.data.di

import cm.trixobase.camermeteo.data.model.weather.Weather

/*
 * Powered by Trixobase Enterprise on 23/04/26
 */

data class JsonResponse(
    val id: Int,
    val current: Weather,
    val location: String
)
