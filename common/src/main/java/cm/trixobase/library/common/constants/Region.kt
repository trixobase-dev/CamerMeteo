package cm.trixobase.library.common.constants

import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 27/04/26
 */

enum class Region {

    ADAMAOUA {
        override val nom = "Adamaoua"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_adamaoua
    },
    CENTRE {
        override val nom = "Centre"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_center
    },
    EST {
        override val nom = "Est"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_east
    },
    EXTREME_NORD {
        override val nom = "Extrême Nord"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_north_far
    },
    LITTORAL {
        override val nom = "Littoral"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_littoral
    },
    NORD {
        override val nom = "Nord"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_north
    },
    NORD_OUEST {
        override val nom = "Nord Ouest"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_north_west
    },
    OUEST {
        override val nom = "Ouest"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_west
    },
    SUD {
        override val nom = "Sud"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_south
    },
    SUD_OUEST {
        override val nom = "Sud Ouest"
        override val description = "Ville du Cameroun"
        override val picture = R.drawable.iv_region_south_west
    };

    abstract val nom: String
    abstract val description: String
    abstract val picture: Int

    override fun toString(): String {
        return "[$nom: $description]"
    }

}