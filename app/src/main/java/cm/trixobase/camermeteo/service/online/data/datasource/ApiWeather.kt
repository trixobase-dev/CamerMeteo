package cm.trixobase.camermeteo.service.online.data.datasource

import cm.trixobase.camermeteo.service.online.domain.model.MeteoResponse
import retrofit2.Response

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

interface ApiWeather {

    //@GET("/point/final")
    suspend fun getMeteoState(): Response<MeteoResponse>

}

