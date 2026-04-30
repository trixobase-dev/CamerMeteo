package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.data.di.Network
import cm.trixobase.camermeteo.data.model.Weather
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.constants.Town
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
        var city = ""
        var unity = ""
        viewModelScope.launch {
            repository.getMyTown(context).collect {
                city = it.data!!
            }
            repository.getMyUnity(context).collect {
                unity = it.data!!
            }
            _uiState.value = HomeUiState(city = city, unity = unity)
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val state = uiState.value!!
            repository.getWeather(city = getCity(state.city), units = getUnits(state.unity)).collect { result ->
                _uiState.value = when (result) {
                    is Network.Success ->
                        HomeUiState(
                            city = state.city,
                            unity = state.unity,
                            temps = buildDemoTemps(result.data),
                            isLoading = false
                        )
                    else ->
                        HomeUiState(city = state.city, unity = state.unity, error = result.error, isLoading = false)
                }
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            val state = uiState.value!!
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is Network.Success ->
                        HomeUiState(city = state.city, unity = state.unity, temps = result.data!!, isLoading = false)
                    else ->
                        HomeUiState(city = state.city, unity = state.unity, error = result.error, isLoading = false)
                }
            }
        }
    }

    private fun getCity(city: String): Town {
        return Town.entries.filter { city == it.nom }[0]
    }

    private fun getUnits(unity: String): String {
        return AppModule.TEMPERATURE.entries.filter { unity == it.unity }[0].units
    }

    private fun buildDemoTemps(data: Weather?): List<UiTemp> {
        return arrayListOf(UiTemp.builder()
                .withHour(10)
                .withTemperature(data?.temperature?.max?.toInt() ?: 25)
                .withPrecipitation(
                    hasRain = true,
                    hasVent = true,
                    hasSun = false))
    }

}