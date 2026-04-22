package cm.trixobase.camermeteo.data.repository

import android.content.Context
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.model.weather.Weather
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.AttributesNames
import cm.trixobase.library.common.Manager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class WeatherRepository {

    private val api = AppModule.weatherApi

    fun fetchMyTown(context: Context): Flow<String> = flow {
        emit(Manager.get(context, AttributesNames.KEY_APP_TOWN, AttributesNames.TOWN_YAOUNDE))
    }

    fun fetchWeather(city: String): Flow<Response<Weather>> = flow {
        delay(2500)
        try {
            val response = api.getWeather(apiKey = AppModule.API_KEY, city)
            emit(response)
        } catch (e: Exception) {
            emit(Response.error(404, e.message.toString().toResponseBody(null)))
        }
    }

    fun fetchDemo(): Flow<List<UiTemp>> = flow {
        delay(2500)
        emit(UiTemp.getAll())
    }
}


