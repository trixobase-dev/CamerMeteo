package cm.trixobase.camermeteo.ui

import cm.trixobase.camermeteo.backend.Manager
import cm.trixobase.camermeteo.common.AttributesNames

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetTownChosen(): String {
        return Manager.get(
            applicationContext,
            AttributesNames.KEY_APP_TOWN,
            AttributesNames.TOWN_YAOUNDE
        )
    }

    open fun doConfigTown(townChosen: String) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_TOWN, townChosen)
    }

    open fun doConfigSong(isOn: Boolean) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_SONG, isOn)
    }

    open fun doConfigRefreshAuto(isOn: Boolean) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_REFRESH_AUTO, isOn)
    }

}