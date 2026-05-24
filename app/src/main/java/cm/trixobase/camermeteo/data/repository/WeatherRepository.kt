package cm.trixobase.camermeteo.data.repository

import android.content.Context
import android.icu.util.Calendar
import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.RequestResult
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class WeatherRepository {

    private val openMeteo = AppModule.weatherApi

    fun getMyData(context: Context): Flow<RequestResult<MutableMap<String, String>>> = flow {
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
        emit(RequestResult.Success(data))
    }

    fun getWeather(language: String, latitude: String, longitude: String): Flow<RequestResult<ApiResult>> = flow {
        try {
            val response = openMeteo.getWeather(
                lang = language,
                units = Temperature.CELSIUS.units,
                date = getCurrentDate(),
                lat = latitude,
                lon = longitude
            )
            val result = when (response.code()) {
                200 -> RequestResult.Success(response.body())
                else -> RequestResult.Error(response.errorBody()?.source().toString())
            }
            emit(result)
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message!!))
        }
    }

    fun getDemo(): Flow<RequestResult<ApiResult>> = flow {
        try {
            delay(1500)
            val weather = ApplicationManager.getWeatherDemo()
            emit(RequestResult.Success(weather))
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message!!))
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


