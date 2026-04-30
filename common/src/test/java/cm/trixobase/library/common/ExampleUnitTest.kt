package cm.trixobase.library.common

import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Town
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun myTest() {
        Town.entries.filter { Region.CENTRE.nom == it.region }.forEach {
            println(it)
        }
    }
    @Test
    fun myTest1() {
        TEMPERATURE.entries.forEach {
            println(it)
        }
    }
}

enum class TEMPERATURE(val units: String, val unity: String) {
    CELSIUS(units = "metric", unity = "°C"),
    FAHRENHEIT(units = "imperial", unity = "°F"),
    KELVIN(units = "standard", unity = "°K");

    override fun toString(): String {
        return "[$name ($unity) => $units]"
    }
}