package cm.trixobase.camermeteo

import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.Utils
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Town
import cm.trixobase.library.common.ui.GlobalActivity

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetConfigTown(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_TOWN,
            Town.YAOUNDE.name
        )
    }

    open fun doGetConfigRegion(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_REGION,
            Region.CENTRE.name
        )
    }

    open fun doGetConfigTemperatureUnity(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_TEMPERATURE_UNITY,
            AttributeNames.TEMPERATURE_UNITY_CELSIUS
        )
    }

    open fun doGetConfigSong(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_SONG,
            true
        )
    }

    open fun doGetConfigDemo(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_DEMO_CONFIGURATION,
            true
        )
    }

    open fun doGetConfigRefreshAuto(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_REFRESH_AUTO,
            true
        )
    }

    open fun doConfigRegion(regionChosen: String) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_REGION, regionChosen)
    }

    open fun doConfigTown(townChosen: String) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_TOWN, townChosen)
    }

    open fun doConfigTemperatureUnity(unity: String) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_TEMPERATURE_UNITY, unity)
    }

    open fun doConfigSong(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_SONG, isOn)
    }

    open fun doConfigDemo(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_DEMO_CONFIGURATION, isOn)
    }

    open fun doConfigRefreshAuto(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_REFRESH_AUTO, isOn)
    }

}