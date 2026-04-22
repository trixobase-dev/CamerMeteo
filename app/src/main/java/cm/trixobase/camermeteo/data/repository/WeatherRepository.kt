package cm.trixobase.camermeteo.data.repository

import android.content.Context
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.di.JsonResponse
import cm.trixobase.camermeteo.data.di.Network
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.AttributesNames
import cm.trixobase.library.common.Tools
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class WeatherRepository {

    private val api = AppModule.weatherApi

    fun getMyTown(context: Context): Flow<Network<String>> = flow {
        val town =
            Tools.process.get(context, AttributesNames.KEY_APP_TOWN, AttributesNames.TOWN_YAOUNDE)
        emit(Network.Success(town))
    }

    fun getWeather(city: String): Flow<Network<JsonResponse>> = flow {
        try {
            emit(Network.Loading())
            delay(2500)

            val jsonResponse = api.getWeather(apiKey = AppModule.API_KEY, city = city).body()
            emit(Network.Success(jsonResponse))
        } catch (e: Exception) {
            emit(Network.Error(e.message!!))
        }
    }

    fun getDemo(): Flow<Network<List<UiTemp>>> = flow {
        emit(Network.Loading())
        delay(2500)
        val temps = UiTemp.getAll()
        emit(Network.Success(temps))
    }

}


