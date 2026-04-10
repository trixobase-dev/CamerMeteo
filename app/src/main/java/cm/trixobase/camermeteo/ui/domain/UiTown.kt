package cm.trixobase.camermeteo.ui.domain

import cm.trixobase.camermeteo.R
import cm.trixobase.camermeteo.common.AttributesNames

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
            instance.picture = R.drawable.ic_launcher_background
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
                    .withName(AttributesNames.TOWN_YAOUNDE)
                    .withDescription("Partiellement nuageux")
                    .withTemperature(27)
                    .withPicture(R.drawable.iv_yaounde)
                    .build()
            )
            return towns
        }
    }

}