package cm.trixobase.camermeteo.ui.view.main

import cm.trixobase.camermeteo.ui.viewui.UiTemp

/*
 * Powered by Trixobase Enterprise on 23/04/26
 */

data class MainUiState(
    val city: String = "Missing city",
    val temps: List<UiTemp> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String = "Missing error"
)
