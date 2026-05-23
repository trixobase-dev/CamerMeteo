@file: Suppress("unused")

package cm.trixobase.camermeteo

import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.ui.domain.GlobalActivity
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class ApplicationActivity : GlobalActivity() {

    open fun doGetConfigLanguage(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_LANGUAGE,
            Language.FRENCH.name
        )
    }

    open fun doGetConfigCity(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_CITY,
            City.YAOUNDE.name
        )
    }

    open fun doGetConfigRegion(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_REGION,
            Region.CENTRE.name
        )
    }

    open fun doGetConfigTemperature(): String {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_TEMPERATURE,
            Temperature.CELSIUS.name
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
            false
        )
    }

    open fun doGetConfigLocalisation(): Boolean {
        return Utils.process.get(
            applicationContext,
            AttributeNames.KEY_APP_LOCALISATION_AUTO,
            false
        )
    }

    open fun doConfigRegion(region: Region) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_REGION, region.name)
    }

    open fun doConfigCity(city: City) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_CITY, city.name)
    }

    open fun doConfigTemperature(temperature: Temperature) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_TEMPERATURE, temperature.name)
    }

    open fun doConfigNotificationSun(isOn: Boolean) {
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_NOTIFICATION_SUN, isOn)
    }

    open fun doConfigLanguage(language: Language) {
        Utils.phone.setLanguage(this, language.unit)
        Utils.process.set(applicationContext, AttributeNames.KEY_APP_LANGUAGE, language.name)
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