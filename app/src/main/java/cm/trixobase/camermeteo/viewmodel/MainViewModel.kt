package cm.trixobase.camermeteo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.AttributesNames
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repository = WeatherRepository()

    private val _myTown = MutableLiveData(AttributesNames.TOWN_YAOUNDE)
    val myTown: LiveData<String> = _myTown

    private val _data = MutableLiveData<List<UiTemp>>()
    val data: LiveData<List<UiTemp>> = _data

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    fun getMyTown(context: Context) {
        viewModelScope.launch {
            repository.fetchMyTown(context).collect { town ->
                _myTown.postValue(town)
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            repository.fetchWeather(myTown.value!!).collect { result ->
                if (result.isSuccessful) {
                    val weather = result.body()
                    _data.postValue(
                        listOf(
                            UiTemp.builder()
                                .withHour(10)
                                .withTemperature(weather?.temperature?.value ?: 25)
                                .withPrecipitation(hasRain = true, hasVent = true, hasSun = false)
                        )
                    )
                } else
                    _error.postValue(result.errorBody()?.source().toString())
                _isLoading.postValue(false)
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            repository.fetchDemo().collect { result ->
                _data.postValue(result)
                _isLoading.postValue(false)
            }
        }
    }

}