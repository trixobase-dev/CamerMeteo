package cm.trixobase.camermeteo.data.repository

import android.content.Context
import android.icu.util.Calendar
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.di.Network
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.Tools
import cm.trixobase.library.common.constants.Town
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
            Tools.process.get(context, AttributeNames.KEY_APP_TOWN, Town.YAOUNDE.name)
        emit(Network.Success(town))
    }

    fun getMyUnity(context: Context): Flow<Network<String>> = flow {
        val unity =
            Tools.process.get(context, AttributeNames.KEY_APP_TEMPERATURE_UNITS, AttributeNames.TEMPERATURE_UNITS_CELSIUS)
        emit(Network.Success(unity))
    }

    fun getWeather(city: Town, units: String): Flow<Network<Weather>> = flow {
        try {
            val response = api.getWeather(units = units, date = getCurrentDate(), lat = city.lat, lon = city.lon)
            val result = when (response.code()) {
                200 -> Network.Success(response.body())
                else -> Network.Error(response.errorBody()?.source().toString())
            }
            emit(result)
        } catch (e: Exception) {
            emit(Network.Error(e.message!!))
        }
    }

    fun getDemo(): Flow<Network<List<UiTemp>>> = flow {
        try {
            delay(2500)
            val temps = UiTemp.getAll()
            emit(Network.Success(temps))
        } catch (e: Exception) {
            emit(Network.Error(e.message!!))
        }
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return "$year-${compute(month)}-${compute(day)}"
    }

    private fun compute(time: Int): String {
        val t = time.toString()
        return if (t.length > 1) t else "0$t"
    }
}


