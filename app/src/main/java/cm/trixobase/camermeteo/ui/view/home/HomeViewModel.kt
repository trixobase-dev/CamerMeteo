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
import cm.trixobase.library.common.domain.RequestResult
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
    private val _notifications = MutableLiveData<Set<String>>()
    val notifications: LiveData<Set<String>> = _notifications

    fun refreshData(context: Context) {
        viewModelScope.launch {
            repository.getMyData(context).collect {
                _uiState.value = buildState(it.data!!)
            }
        }
    }

    fun getMyData(context: Context) {
        viewModelScope.launch {
            val state = _uiState.value ?: HomeUiState.started
            repository.getMyData(context).collect {
                val data = it.data!!

                val city = City.entries.filter { d -> data["city"]!! == d.name }[0]
                val temperature = Temperature.entries.filter { d -> data["temperature"]!! == d.name }[0]

                val isLocalisation = data["gps"]!!.toBoolean()
                val isDemo = data["demo"]!!.toBoolean()

                if (state.isStarted
                    || (state.city.name != city.name)
                    || (state.temperature.name != temperature.name)
                    || (state.isLocalisation != isLocalisation)
                    || (state.isDemo != isDemo)
                ) {
                    _uiState.value = buildState(data)
                }
            }
        }
    }

    fun getWeatherData(context: Context) {
        viewModelScope.launch {
            val state = uiState.value!!

            if (!Utils.phone.hasInternet(context)) _uiState.value =
                state.error(context.getString(R.string.warning_connection_internet))
            else repository.getWeather(context).collect { result ->
                _uiState.value = when (result) {
                    is RequestResult.Success -> {
                        val weather = result.data!!
                        state.update(weather)
                    }
                    else -> state.error(result.error)
                }
            }
        }
    }

    fun getNotificationData(context: Context) {
        viewModelScope.launch {
            repository.getNotification(context).collect { result ->
                _notifications.value = when (result) {
                    is RequestResult.Success -> {
                        val notes = mutableSetOf("")
                        result.data!!["notifications"]!!.split(",").forEach { notes.add(it) }
                        notes
                    }
                    else -> setOf()
                }
            }
        }
    }

    fun getWeatherDemo() {
        viewModelScope.launch {
            val state = uiState.value!!
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is RequestResult.Success -> {
                        val weather = result.data!!
                        state.update(weather)
                    }
                    else -> state.error(result.error)
                }
            }
        }
    }

    private fun buildState(data: MutableMap<String, String>): HomeUiState {
        return HomeUiState(
            language = Language.entries.filter { d -> data["language"]!! == d.name }[0],
            region = Region.entries.filter { d -> data["region"]!! == d.name }[0],
            city = City.entries.filter { d -> data["city"]!! == d.name }[0],
            temperature = Temperature.entries.filter { d -> data["temperature"]!! == d.name }[0],
            isLocalisation = data["gps"]!!.toBoolean(),
            isDemo = data["demo"]!!.toBoolean(),
            isLoading = true,
        )
    }

}