package cm.trixobase.camermeteo.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.common.AttributesNames
import cm.trixobase.camermeteo.ui.UiTemp
import cm.trixobase.camermeteo.ui.model.Repository
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class MainViewModel : ViewModel() {

    val repository = Repository()

    private val _myTown = MutableLiveData(AttributesNames.TOWN_YAOUNDE)
    val myTown: LiveData<String> = _myTown

    private val _data = MutableLiveData<List<UiTemp>>()
    val data: LiveData<List<UiTemp>> = _data

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMyTown(context: Context) {
        viewModelScope.launch {
            val result = repository.fetchMyTown(context)
            _myTown.postValue(result)
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val result = repository.fetchData()
            _data.postValue(result)
            _isLoading.postValue(false)
        }
    }
}