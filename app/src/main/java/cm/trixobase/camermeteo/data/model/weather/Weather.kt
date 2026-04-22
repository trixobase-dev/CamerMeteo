package cm.trixobase.camermeteo.data.model.weather

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

data class Weather (
    val temperature: Temperature,
    val last_updated: String,
    val temp_c: String,
    val temp_f: String,
    val condition: Condition
)