package cm.trixobase.camermeteo.ui

import cm.trixobase.camermeteo.backend.Manager
import cm.trixobase.camermeteo.common.AttributesNames
import cm.trixobase.camermeteo.common.ui.GlobalActivity

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetConfigTown(): String {
        return Manager.get(
            applicationContext,
            AttributesNames.KEY_APP_TOWN,
            AttributesNames.TOWN_YAOUNDE
        )
    }

    open fun doGetConfigTemperatureUnity(): String {
        return Manager.get(
            applicationContext,
            AttributesNames.KEY_APP_TEMPERATURE_UNITY,
            AttributesNames.UNITY_TEMPERATURE_CELSIUS
        )
    }

    open fun doGetConfigSong(): Boolean {
        return Manager.get(
            applicationContext,
            AttributesNames.KEY_APP_SONG,
            true
        )
    }

    open fun doGetConfigRefreshAuto(): Boolean {
        return Manager.get(
            applicationContext,
            AttributesNames.KEY_APP_REFRESH_AUTO,
            true
        )
    }

    open fun doConfigTown(townChosen: String) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_TOWN, townChosen)
    }

    open fun doConfigTemperatureUnity(unity: String) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_TEMPERATURE_UNITY, unity)
    }

    open fun doConfigSong(isOn: Boolean) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_SONG, isOn)
    }

    open fun doConfigRefreshAuto(isOn: Boolean) {
        Manager.set(applicationContext, AttributesNames.KEY_APP_REFRESH_AUTO, isOn)
    }

}