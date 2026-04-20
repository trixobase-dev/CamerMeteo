package cm.trixobase.camermeteo.ui.model

import android.content.Context
import cm.trixobase.camermeteo.backend.Manager
import cm.trixobase.camermeteo.common.AttributesNames
import cm.trixobase.camermeteo.ui.UiTemp
import kotlinx.coroutines.delay

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

class Repository {

    fun fetchMyTown(context: Context): String {
        return Manager.get(context, AttributesNames.KEY_APP_TOWN, AttributesNames.TOWN_YAOUNDE)
    }

    suspend fun fetchData(): List<UiTemp> {
        delay(2500)
        return UiTemp.getAll()
        //return Weather(Coordinate(15.5, 36.85), Temperature(18.0))
    }

}