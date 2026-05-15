package cm.trixobase.library.common.constants

/*
 * Powered by Trixobase Enterprise on 13/05/26
 */

enum class Language (val unit: String, val display: String) {
    ARABE(unit = "ar", display = "Arabe"),
    CHINESE(unit = "zh", display = "Chinese"),
    GERMANY(unit = "de", display = "Deutsch"),
    ENGLISH(unit = "en", display = "English"),
    SPANISH(unit = "es", display = "Espanol"),
    FRENCH(unit = "fr", display = "Français"),
    ITALIAN(unit = "it", display = "Italian");

    override fun toString(): String {
        return "[$name ($display) => $unit]"
    }
}