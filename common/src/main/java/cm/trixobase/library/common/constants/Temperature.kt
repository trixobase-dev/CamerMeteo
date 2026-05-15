package cm.trixobase.library.common.constants

/*
 * Powered by Trixobase Enterprise on 15/05/26
 */

enum class Temperature(val units: String, val display: String) {
    CELSIUS(units = "metric", display = "°C"),
    FAHRENHEIT(units = "imperial", display = "°F"),
    KELVIN(units = "standard", display = "°K");

    override fun toString(): String {
        return "[$name ($display) => $units]"
    }
}