package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.ui.viewui.UiTemp

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

data class HomeUiState(
    val region: String = "",
    val city: String = "",
    var unity: String = "",
    val temps: List<UiTemp> = listOf(),
    val isLoading: Boolean = false,
    val error: String = ""
) {

    fun builder(weather: List<UiTemp>): HomeUiState {
        return HomeUiState(
            region = this.region,
            city = this.city,
            unity = this.unity,
            temps = weather)
    }

    fun builder(error: String): HomeUiState {
        return HomeUiState(
            region = this.region,
            city = this.city,
            unity = this.unity,
            error = error,
            temps = this.temps)
    }
}