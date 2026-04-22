package cm.trixobase.camermeteo

import cm.trixobase.library.common.AttributesNames
import cm.trixobase.library.common.Tools
import cm.trixobase.library.common.ui.GlobalActivity

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetConfigTown(): String {
        return Tools.process.get(
            applicationContext,
            AttributesNames.KEY_APP_TOWN,
            AttributesNames.TOWN_YAOUNDE
        )
    }

    open fun doGetConfigTemperatureUnity(): String {
        return Tools.process.get(
            applicationContext,
            AttributesNames.KEY_APP_TEMPERATURE_UNITY,
            AttributesNames.UNITY_TEMPERATURE_CELSIUS
        )
    }

    open fun doGetConfigSong(): Boolean {
        return Tools.process.get(
            applicationContext,
            AttributesNames.KEY_APP_SONG,
            true
        )
    }

    open fun doGetConfigRefreshAuto(): Boolean {
        return Tools.process.get(
            applicationContext,
            AttributesNames.KEY_APP_REFRESH_AUTO,
            true
        )
    }

    open fun doConfigTown(townChosen: String) {
        Tools.process.set(applicationContext, AttributesNames.KEY_APP_TOWN, townChosen)
    }

    open fun doConfigTemperatureUnity(unity: String) {
        Tools.process.set(applicationContext, AttributesNames.KEY_APP_TEMPERATURE_UNITY, unity)
    }

    open fun doConfigSong(isOn: Boolean) {
        Tools.process.set(applicationContext, AttributesNames.KEY_APP_SONG, isOn)
    }

    open fun doConfigRefreshAuto(isOn: Boolean) {
        Tools.process.set(applicationContext, AttributesNames.KEY_APP_REFRESH_AUTO, isOn)
    }

}