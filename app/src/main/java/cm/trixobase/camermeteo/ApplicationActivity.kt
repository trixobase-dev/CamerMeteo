@file: Suppress("unused")

package cm.trixobase.camermeteo

import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Town
import cm.trixobase.library.common.ui.GlobalActivity
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetConfigLanguage(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_LANGUAGE,
            AttributeNames.LANGUAGE_FRENCH
        )
    }

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

    open fun doGetConfigNoteSun(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_NOTIFICATION_SUN,
            true
        )
    }

    open fun doGetConfigNoteRain(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_NOTIFICATION_RAIN,
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

    open fun doGetConfigLocalisation(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_LOCALISATION_AUTO,
            false
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

    open fun doConfigNotificationSun(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_NOTIFICATION_SUN, isOn)
    }

    open fun doConfigLanguage(language: String) {
        Utils.phone.updateLanguage(this, language)
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_LANGUAGE, language)
    }

    open fun doConfigLocalisationAuto(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_LOCALISATION_AUTO, isOn)
    }

    open fun doConfigNotificationRain(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_NOTIFICATION_RAIN, isOn)
    }

    open fun doConfigDemo(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_DEMO_CONFIGURATION, isOn)
    }

}