package cm.trixobase.camermeteo.ui.view.main

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.di.Network
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

@OptIn(InternalComposeApi::class)
class MainViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val _uiState = MutableLiveData<MainUiState>()

    val uiState: LiveData<MainUiState> = _uiState

    fun getMyTown(context: Context) {
        viewModelScope.launch {
            repository.getMyTown(context).collect {
                _uiState.value = MainUiState(city = it.data!!, isLoading = true)
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val city = uiState.value?.city!!
            repository.getWeather(city = city).collect { result ->
                _uiState.value = when (result) {
                    is Network.Loading ->
                        MainUiState(city = city, isLoading = true)
                    is Network.Success ->
                        MainUiState(
                            city = city, temps = listOf(
                                UiTemp.builder()
                                    .withHour(10)
                                    .withTemperature(result.data?.current?.temperature?.value ?: 25)
                                    .withPrecipitation(
                                        hasRain = true,
                                        hasVent = true,
                                        hasSun = false
                                    )
                            )
                        )
                    else ->
                        MainUiState(city = city, error = result.error)
                }
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            val city = uiState.value?.city!!
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is Network.Loading ->
                        MainUiState(city = city, isLoading = true)
                    is Network.Success ->
                        MainUiState(city = uiState.value?.city!!, temps = result.data!!)
                    else ->
                        MainUiState(city = city, error = result.error)
                }
            }
        }
    }

}