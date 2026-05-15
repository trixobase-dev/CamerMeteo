package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.data.datasource.ApiResult
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

data class HomeUiState(
    val weather: ApiResult? = null,
    val language: Language,
    val region: Region,
    val city: City,
    var temperature: Temperature,
    val isLocalisation: Boolean = false,
    val isDemo: Boolean = false,
    val temps: List<UiTemp> = listOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val isStarted: Boolean = true
) {

    fun builder(temps: List<UiTemp>): HomeUiState {
        return HomeUiState(
            language = this.language,
            region = this.region,
            city = this.city,
            temperature = this.temperature,
            isLocalisation = this.isLocalisation,
            isStarted = false,
            isDemo = this.isDemo,
            temps = temps)
    }

    fun builder(error: String): HomeUiState {
        return HomeUiState(
            language = this.language,
            region = this.region,
            city = this.city,
            temperature = this.temperature,
            isLocalisation = this.isLocalisation,
            isStarted = false,
            isDemo = this.isDemo,
            error = error)
    }

    companion object {

        val started = HomeUiState(
            language = Language.FRENCH,
            region = Region.CENTRE,
            city = City.YAOUNDE,
            temperature = Temperature.CELSIUS,
            isStarted = true
        )

    }

}