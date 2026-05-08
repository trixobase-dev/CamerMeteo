package cm.trixobase.camermeteo.data.repository

import android.content.Context
import android.icu.util.Calendar
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.Utils
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Town
import cm.trixobase.library.common.utils.NetworkResult
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
        data["region"] =
            Utils.process.get(context, AttributeNames.KEY_APP_REGION, Region.CENTRE.name)
        data["city"] =
            Utils.process.get(context, AttributeNames.KEY_APP_TOWN, Town.YAOUNDE.name)
        data["unity"] =
            Utils.process.get(
                context,
                AttributeNames.KEY_APP_TEMPERATURE_UNITY,
                AttributeNames.TEMPERATURE_UNITY_CELSIUS
            )
        emit(NetworkResult.Success(data))
    }

    fun getWeather(town: String, unity: String): Flow<NetworkResult<Weather>> = flow {
        try {
            val city = getCity(town)
            val response = api.getWeather(
                units = getUnits(unity),
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

    private fun getCity(city: String): Town {
        return Town.entries.filter { city == it.name }[0]
    }

    private fun getUnits(unity: String): String {
        return AppModule.TEMPERATURE.entries.filter { unity == it.unity }[0].units
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


