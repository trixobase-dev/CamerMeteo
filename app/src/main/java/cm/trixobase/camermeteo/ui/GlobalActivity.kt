package cm.trixobase.camermeteo.ui

import androidx.activity.ComponentActivity
import cm.trixobase.camermeteo.backend.Manager
import cm.trixobase.camermeteo.common.AttributesNames

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class GlobalActivity: ComponentActivity() {

    open fun doGetTownChosen(): String {
        return Manager.get(applicationContext, AttributesNames.KEY_APP_TOWN, AttributesNames.TOWN_YAOUNDE)
    }

    open fun doSetTownChosen(townChosen: String) {
        return Manager.set(applicationContext, AttributesNames.KEY_APP_TOWN, townChosen)
    }

}