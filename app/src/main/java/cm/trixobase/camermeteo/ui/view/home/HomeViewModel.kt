package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.utils.NetworkResult
import cm.trixobase.library.common.utils.Utils
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
            val state = _uiState.value?: HomeUiState.started
            repository.getMyData(context).collect {
                val data = it.data!!

                val language = Language.entries.filter {d-> data["language"]!! == d.name }[0]
                val region = Region.entries.filter { d-> data["region"]!! == d.name }[0]
                val city = City.entries.filter { d-> data["city"]!! == d.name }[0]
                val temperature = Temperature.entries.filter { d-> data["temperature"]!! == d.name }[0]

                val isLocalisation = data["gps"]!!.toBoolean()
                val isDemo = data["demo"]!!.toBoolean()

                if (state.isStarted || (state.city.name != city.name) || (state.isDemo != isDemo) || (state.isLocalisation != isLocalisation) || (state.temperature.name != temperature.name)) {
                    _uiState.value = HomeUiState(
                        language = language,
                        region = region,
                        city = city,
                        temperature = temperature,
                        isLocalisation = isLocalisation,
                        isDemo = isDemo,
                        isLoading = true,
                    )
                } else
                    _uiState.value?.temperature = temperature
            }
        }
    }

    fun getWeatherData(context: Context) {
        viewModelScope.launch {
            val stateHome = uiState.value!!

            if (!Utils.phone.hasInternet(context))
                _uiState.value = stateHome.builder(context.getString(R.string.warning_internet_connection))
            else
                repository.getWeather(
                    language = stateHome.language.unit,
                    city = stateHome.city,
                    units = stateHome.temperature.units
                ).collect { result ->
                    _uiState.value = when (result) {
                        is NetworkResult.Success ->
                            stateHome.builder(result.data!!)
                        else ->
                            stateHome.builder(result.error)
                    }
                }
        }
    }

    fun getWeatherDemo(context: Context) {
        viewModelScope.launch {
            val stateHome = uiState.value!!

            if (!Utils.phone.hasInternet(context))
                _uiState.value = stateHome.builder(context.getString(R.string.warning_internet_connection))
            else
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

}