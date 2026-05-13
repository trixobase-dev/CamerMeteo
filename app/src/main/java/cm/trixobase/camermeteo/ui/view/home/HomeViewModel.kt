package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.datasource.ApiResult
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
                val language = data["lang"]!!
                val region = data["region"]!!
                val city = data["city"]!!
                val unity = data["unity"]!!
                val isDemo = data["demo"]!!.toBoolean()

                if (_uiState.value?.city != city) {
                    _uiState.value = HomeUiState(
                        language = language,
                        region = region,
                        city = city,
                        unity = unity,
                        isDemo = isDemo,
                        isLoading = true
                    )
                } else {
                    _uiState.value?.unity = unity
                    if (_uiState.value?.isDemo != isDemo)
                        _uiState.value = HomeUiState(
                            language = language,
                            region = region,
                            city = city,
                            unity = unity,
                            isDemo = isDemo,
                            isLoading = true
                        )
                }
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val stateHome = uiState.value!!
            repository.getWeather(
                language = stateHome.language,
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

    private fun buildWeather(data: ApiResult?): List<UiTemp> {
        return arrayListOf(
            UiTemp.builder()
                .withHour(10)
                .withTemperature(data?.main?.temp_max!!.toInt())
                .withPrecipitation(
                    hasRain = true,
                    hasVent = true,
                    hasSun = false
                )
        )
    }

}