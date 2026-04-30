package cm.trixobase.camermeteo.ui.view.home

import cm.trixobase.camermeteo.ui.viewui.UiTemp

/*
 * Powered by Trixobase Enterprise on 12/04/26
 */

data class HomeUiState(
    val city: String = "",
    val unity: String = "",
    val temps: List<UiTemp> = arrayListOf(),
    val isLoading: Boolean = true,
    val error: String = ""
)