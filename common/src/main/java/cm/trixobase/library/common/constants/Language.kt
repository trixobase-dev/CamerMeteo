package cm.trixobase.library.common.constants

/*
 * Powered by Trixobase Enterprise on 13/05/26
 */

enum class Language (val unit: String, val language: String) {
    FRENCH(unit = "fr", language = "Français"),
    ENGLISH(unit = "en", language = "English"),
    SPANISH(unit = "sp", language = "Espanol");

    override fun toString(): String {
        return "[$name ($language) => $unit]"
    }
}