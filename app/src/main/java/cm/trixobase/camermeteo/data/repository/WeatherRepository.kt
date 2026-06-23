package cm.trixobase.camermeteo.data.repository

import android.content.Context
import cm.trixobase.camermeteo.App
import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.domain.RequestResult
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
        showLog("getMyData", "Request end with data: [$data]")
        emit(RequestResult.Success(data))
    }

    fun getWeather(context: Context): Flow<RequestResult<ApiResult>> = flow {
        val location = App.getLocation(context)
        try {
            showLog("getWeather", "Request start")
            val response = openMeteo.getWeather(
                lang = App.getLanguage(context).unit,
                units = Temperature.CELSIUS.units,
                lat = location["latitude"]!!,
                lon = location["longitude"]!!
            )
            val result = when (response.code()) {
                200 -> RequestResult.Success(response.body())
                401, 403 -> RequestResult.Error(context.getString(R.string.warning_connection_unauthorized))
                in 500..505 -> RequestResult.Error(context.getString(R.string.warning_connection_internal_error))
                else -> RequestResult.Error(response.errorBody()?.source().toString())
            }
            showLog("getWeather", "Request end with result: [$result]")
            emit(result)
        } catch (e: Exception) {
            val message = e.message!!
            val error = when {
                message.contains("timed out", true) ||
                message.contains("timeout", true)
                    ->  context.getString(R.string.warning_connection_time_out)
                message.contains("unable to resolve host", true)
                        -> context.getString(R.string.warning_connection_resolve_host)
                message.contains("failed to connect", true)
                        -> context.getString(R.string.warning_connection_failed)
                else -> message
            }
            showLog("getWeather", "Request end with error: $error")
            emit(RequestResult.Error(error))
        }
    }

    fun getNotification(context: Context): Flow<RequestResult<MutableMap<String, String>>> = flow {
        val data = mutableMapOf<String, String>()
        data["notifications"] =
            Utils.process.get(context, AttributeNames.KEY_APP_NOTIFICATIONS, "")
        showLog("getNotification", "Request end with data: $data")
        emit(RequestResult.Success(data))
    }

    fun getDemo(): Flow<RequestResult<ApiResult>> = flow {
        try {
            delay(1500)
            val weather = App.getWeatherDemo()
            emit(RequestResult.Success(weather))
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message!!))
        }
    }

    private fun showLog(method: String, error: String) {
        Utils.process.showLog("WeatherRepository", method, error)
    }

}


