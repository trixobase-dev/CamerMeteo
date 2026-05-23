package cm.trixobase.camermeteo.ui.view.home

import android.content.Context
import androidx.compose.runtime.InternalComposeApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.data.AppDatabase
import cm.trixobase.library.common.data.repository.NotificationRepository
import cm.trixobase.library.common.utils.RequestResult
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
        val state = _uiState.value?: HomeUiState.started
        val db = AppDatabase.getInstance(context)
        state.notifications = NotificationRepository(db).fetchAll()

        viewModelScope.launch {
            repository.getMyData(context).collect {
                val data = it.data!!

                val language = Language.entries.filter {d-> data["language"]!! == d.name }[0]
                val region = Region.entries.filter { d-> data["region"]!! == d.name }[0]
                val city = City.entries.filter { d-> data["city"]!! == d.name }[0]
                val temperature = Temperature.entries.filter { d-> data["temperature"]!! == d.name }[0]

                val isLocalisation = data["gps"]!!.toBoolean()
                val isDemo = data["demo"]!!.toBoolean()

                if (state.isStarted
                    || (state.language.name != language.name)
                    || (state.city.name != city.name)
                    || (state.temperature.name != temperature.name)
                    || (state.isLocalisation != isLocalisation)
                    || (state.isDemo != isDemo) ) {
                    _uiState.value = HomeUiState(
                        notifications = state.notifications,
                        language = language,
                        region = region,
                        city = city,
                        temperature = temperature,
                        isLocalisation = isLocalisation,
                        isDemo = isDemo,
                        isLoading = true,
                    )
                }
            }
        }
    }

    fun getWeatherData(context: Context) {
        val stateHome = uiState.value!!
        viewModelScope.launch {

            if (!Utils.phone.hasInternet(context))
                _uiState.value = stateHome.error(context.getString(R.string.warning_internet_connection))
            else
                repository.getWeather(
                    language = stateHome.language.unit,
                    city = stateHome.city,
                    units = stateHome.temperature.units
                ).collect { result ->
                    _uiState.value = when (result) {
                        is RequestResult.Success -> {
                            val weather = result.data!!
                            ApplicationManager.setWeatherNotification(context, weather)
                            stateHome.update(weather)
                        }
                        else ->
                            stateHome.error(result.error)
                    }
                }
        }
    }

    fun getWeatherDemo(context: Context) {
        val stateHome = uiState.value!!
        viewModelScope.launch {
            repository.getDemo().collect { result ->
                _uiState.value = when (result) {
                    is RequestResult.Success -> {
                        val weather = result.data!!
                        ApplicationManager.setWeatherNotification(context, weather)
                        stateHome.update(result.data!!)
                    }
                    else ->
                        stateHome.error(result.error)
                }
            }
        }
    }

}