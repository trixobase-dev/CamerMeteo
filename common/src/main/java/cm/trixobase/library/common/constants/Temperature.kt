package cm.trixobase.library.common.constants

/*
 * Powered by Trixobase Enterprise on 15/05/26
 */

enum class Temperature(val units: String, val display: String, val unity: String) {
    CELSIUS(units = "metric", display = "°C", unity = "°"),
    FAHRENHEIT(units = "imperial", display = "°F", unity = "°"),
    KELVIN(units = "standard", display = "°K", unity = "°");

    override fun toString(): String {
        return "[$name ($display-$unity) => $units]"
    }
}