package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.data.AppDatabase
import cm.trixobase.library.common.data.model.Notification
import cm.trixobase.library.common.data.repository.NotificationRepository
import cm.trixobase.library.common.utils.RequestResult
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

@OptIn(InternalComposeApi::class)
class HomeViewModel : ViewModel() {

    val location = mutableMapOf<String, String>()
    private val repository = WeatherRepository()
    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState

    fun refreshData(context: Context) {
        viewModelScope.launch {
            repository.getMyData(context).collect {
                val data = it.data!!
                val notifications =
                    NotificationRepository(AppDatabase.getInstance(context)).fetchAll()

                _uiState.value = buildState(data, notifications)
            }
        }

    }

    fun getMyData(context: Context) {
        viewModelScope.launch {
            val state = _uiState.value ?: HomeUiState.started
            repository.getMyData(context).collect {
                val data = it.data!!
                val notifications =
                    NotificationRepository(AppDatabase.getInstance(context)).fetchAll()

                val language = Language.entries.filter { d -> data["language"]!! == d.name }[0]
                val city = City.entries.filter { d -> data["city"]!! == d.name }[0]
                val temperature =
                    Temperature.entries.filter { d -> data["temperature"]!! == d.name }[0]

                val isLocalisation = data["gps"]!!.toBoolean()
                val isDemo = data["demo"]!!.toBoolean()

                if (state.isStarted
                    || (state.city.name != city.name)
                    || (state.temperature.name != temperature.name)
                    || (state.isLocalisation != isLocalisation)
                    || (state.isDemo != isDemo)
                ) {
                    _uiState.value = buildState(data, notifications)
                }
            }
        }
    }

    fun getWeatherData(context: Context) {
        viewModelScope.launch {
            val state = uiState.value!!
            val locationIsOn =
                Utils.process.get(context, AttributeNames.KEY_APP_LOCALISATION_AUTO, false)
            val latitude = if (locationIsOn) location["latitude"]!! else state.city.lat
            val longitude = if (locationIsOn) location["longitude"]!! else state.city.lon

            if (!Utils.phone.hasInternet(context)) _uiState.value =
                state.error(context.getString(R.string.warning_internet_connection))
            else repository.getWeather(
                language = state.language.unit,
                latitude = latitude,
                longitude = longitude
            ).collect { result ->
                _uiState.value = when (result) {
                    is RequestResult.Success -> {
                        val weather = result.data!!
                        ApplicationManager.setWeatherNotification(context, weather)
                        state.update(weather)
                    }

                    else -> state.error(result.error)
                }
            }
        }
    }

    fun getWeatherDemo(context: Context) {
        viewModelScope.launch {
            val state = uiState.value!!
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is RequestResult.Success -> {
                        val weather = result.data!!
                        ApplicationManager.setWeatherNotification(context, weather)
                        state.update(result.data!!)
                    }

                    else -> state.error(result.error)
                }
            }
        }
    }

    private fun buildState(
        data: MutableMap<String, String>,
        notifications: List<Notification>
    ): HomeUiState {
        return HomeUiState(
            notifications = notifications,
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