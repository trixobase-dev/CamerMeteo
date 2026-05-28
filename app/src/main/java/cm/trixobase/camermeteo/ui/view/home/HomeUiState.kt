package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

data class HomeUiState(
    val weather: HomeUiWeather? = null,
    val language: Language,
    val region: Region,
    val city: City,
    var temperature: Temperature,
    val isLocalisation: Boolean = false,
    val isDemo: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false,
    val isStarted: Boolean = false
) {

    fun update(weather: ApiResult): HomeUiState {
        return HomeUiState(
            language = this.language,
            region = this.region,
            city = this.city,
            temperature = this.temperature,
            isLocalisation = this.isLocalisation,
            isDemo = this.isDemo,
            weather = HomeUiWeather
                .builder(weather, this.temperature)
                .build()
        )
    }

    fun error(error: String): HomeUiState {
        return HomeUiState(
            language = this.language,
            region = this.region,
            city = this.city,
            temperature = this.temperature,
            isLocalisation = this.isLocalisation,
            isDemo = this.isDemo,
            weather = this.weather,
            error = error)
    }

    companion object {

        val started = HomeUiState(
            language = Language.FRENCH,
            region = Region.CENTRE,
            city = City.YAOUNDE,
            temperature = Temperature.CELSIUS,
            isStarted = true)

    }

}