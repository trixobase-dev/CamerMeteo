package cm.trixobase.camermeteo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.common.AttributesNames
import cm.trixobase.camermeteo.common.MyResult
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: WeatherRepository
) : ViewModel() {

    private val _myTown = MutableLiveData(AttributesNames.TOWN_YAOUNDE)
    val myTown: LiveData<String> = _myTown

    private val _data = MutableLiveData<List<UiTemp>>()
    val data: LiveData<List<UiTemp>> = _data

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMyTown(context: Context) {
        viewModelScope.launch {
            repository.fetchMyTown(context).collect { town ->
                _myTown.postValue(town)
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            repository.fetchWeather().collect { result ->
                when(result) {
                    is MyResult.Loading -> {

                    }
                    else -> {}
                }
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            repository.fetchData().collect { result ->
                _data.postValue(result)
                _isLoading.postValue(false)
            }
        }
    }
}