package cm.trixobase.camermeteo.data.repository

import android.content.Context

import cm.trixobase.camermeteo.common.AttributesNames
import cm.trixobase.camermeteo.common.Manager
import cm.trixobase.camermeteo.common.MyResult
import cm.trixobase.camermeteo.data.datasource.WeatherApi
import cm.trixobase.camermeteo.ui.viewui.UiTemp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    fun fetchMyTown(context: Context): Flow<String> = flow {
        emit(Manager.get(context, AttributesNames.KEY_APP_TOWN, AttributesNames.TOWN_YAOUNDE))
    }

    fun fetchWeather() = flow {
        emit(MyResult.Loading())
        val weathers = api.getWeather().body()?.weather
        emit(MyResult.Success(weathers))
    }.catch { error ->
        emit(MyResult.Error(error.message!!))
    }

    fun fetchData(): Flow<List<UiTemp>> = flow {
        delay(2500)
        emit(UiTemp.getAll())
    }

}