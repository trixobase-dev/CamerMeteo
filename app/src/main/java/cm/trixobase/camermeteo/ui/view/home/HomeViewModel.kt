package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.utils.NetworkResult
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

@OptIn(InternalComposeApi::class)
class HomeViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState

    fun getMyData(context: Context) {
        viewModelScope.launch {
            repository.getMyData(context).collect {
                val data = it.data!!
                val region = data["region"]!!
                val city = data["city"]!!
                val unity = data["unity"]!!

                if (_uiState.value?.city != city) {
                    _uiState.value = HomeUiState(
                        region = region,
                        city = city,
                        unity = unity,
                        isLoading = true
                    )
                } else
                    _uiState.value?.unity = unity
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val stateHome = uiState.value!!
            repository.getWeather(
                town = stateHome.city,
                unity = stateHome.unity
            ).collect { result ->
                _uiState.value = when (result) {
                    is NetworkResult.Success ->
                        stateHome.builder(buildWeather(result.data))

                    else ->
                        stateHome.builder(result.error)
                }
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            val stateHome = uiState.value!!
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is NetworkResult.Success ->
                        stateHome.builder(result.data!!)

                    else ->
                        stateHome.builder(result.error)
                }
            }
        }
    }

    private fun buildWeather(data: Weather?): List<UiTemp> {
        return arrayListOf(
            UiTemp.builder()
                .withHour(10)
                .withTemperature(data?.temperature?.max?.toInt() ?: 25)
                .withPrecipitation(
                    hasRain = true,
                    hasVent = true,
                    hasSun = false
                )
        )
    }

}