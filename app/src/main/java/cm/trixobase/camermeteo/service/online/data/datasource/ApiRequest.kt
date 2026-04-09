package cm.trixobase.camermeteo.service.online.data.datasource

import cm.trixobase.camermeteo.service.online.domain.model.MeteoResponse

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

interface ApiRequest {

    //@GET("/point/final")
    suspend fun getMeteos(): Response<MeteoResponse>
}

interface Response<T> {

}
