package cm.trixobase.camermeteo.data.repository

import android.content.Context
import android.icu.util.Calendar
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.NetworkResult
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class WeatherRepository {

    private val api = AppModule.weatherApi

    fun getMyData(context: Context): Flow<NetworkResult<MutableMap<String, String>>> = flow {
        val data = mutableMapOf<String, String>()
        data["language"] =
            Utils.process.get(context, AttributeNames.KEY_APP_LANGUAGE, Language.FRENCH.name)
        data["demo"] =
            Utils.process.get(context, AttributeNames.KEY_APP_DEMO_CONFIGURATION, false).toString()
        data["gps"] =
            Utils.process.get(context, AttributeNames.KEY_APP_LOCALISATION_AUTO, false).toString()
        data["region"] =
            Utils.process.get(context, AttributeNames.KEY_APP_REGION, Region.CENTRE.name)
        data["city"] =
            Utils.process.get(context, AttributeNames.KEY_APP_CITY, City.YAOUNDE.name)
        data["temperature"] =
            Utils.process.get(context, AttributeNames.KEY_APP_TEMPERATURE, Temperature.CELSIUS.name)
        emit(NetworkResult.Success(data))
    }

    fun getWeather(city: City, language: String, units: String): Flow<NetworkResult<ApiResult>> = flow {
        try {
            val response = api.getWeather(
                lang = language,
                units = units,
                date = getCurrentDate(),
                lat = city.lat,
                lon = city.lon
            )
            val result = when (response.code()) {
                200 -> NetworkResult.Success(response.body())
                else -> NetworkResult.Error(response.errorBody()?.source().toString())
            }
            emit(result)
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message!!))
        }
    }

    fun getDemo(): Flow<NetworkResult<List<UiTemp>>> = flow {
        try {
            delay(1500)
            val temps = UiTemp.getAll()
            emit(NetworkResult.Success(temps))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message!!))
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


