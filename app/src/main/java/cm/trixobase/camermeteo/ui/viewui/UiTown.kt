package cm.trixobase.camermeteo.ui.viewui

import cm.trixobase.library.common.constants.Town

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

class UiTown {

    private constructor()

    lateinit var name: String
    lateinit var description: String
    var temperature: Int = 0
    var picture: Int = 0

    class Builder {

        internal constructor() {
            instance.name = "Yaoundé"
            instance.picture = cm.trixobase.library.common.R.drawable.iv_town_yaounde
        }

        private val instance = UiTown()

        fun withName(name: String): Builder {
            instance.name = name
            return this
        }

        fun withDescription(description: String): Builder {
            instance.description = description
            return this
        }

        fun withTemperature(temperature: Int): Builder {
            instance.temperature = temperature
            return this
        }

        fun withPicture(picture: Int): Builder {
            instance.picture = picture
            return this
        }

        fun build(): UiTown {
            return instance
        }


    }

    companion object {
        private fun builder(): Builder {
            return Builder()
        }

        fun getAll(): List<UiTown> {
            val towns = mutableListOf<UiTown>()
            towns.add(
                builder()
                    .withName(Town.YAOUNDE.nom)
                    .withDescription("Partiellement nuageux")
                    .withTemperature(29)
                    .withPicture(Town.YAOUNDE.picture)
                    .build()
            )
            towns.add(
                builder()
                    .withName(Town.DSCHANG.nom)
                    .withDescription("Nuageux, ensolleillé")
                    .withTemperature(30)
                    .withPicture(Town.DSCHANG.picture)
                    .build()
            )
            towns.add(
                builder()
                    .withName(Town.NGAOUNDERE.nom)
                    .withDescription("Pluvieux")
                    .withTemperature(24)
                    .withPicture(Town.NGAOUNDERE.picture)
                    .build()
            )
            towns.add(
                builder()
                    .withName(Town.GAROUA.nom)
                    .withDescription("Temps sec")
                    .withTemperature(30)
                    .withPicture(Town.GAROUA.picture)
                    .build()
            )
            return towns
        }
    }

}