package cm.trixobase.library.common.constants

import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 27/04/26
 */

enum class Region {

    ADAMAOUA {
        override val display = R.string.region_cm_adamaoua
        override val description = R.string.region_cm_adamaoua_description
        override val picture = R.drawable.iv_region_adamaoua
    },
    CENTRE {
        override val display = R.string.region_cm_center
        override val description = R.string.region_cm_center_description
        override val picture = R.drawable.iv_region_center
    },
    EST {
        override val display = R.string.region_cm_east
        override val description = R.string.region_cm_east_description
        override val picture = R.drawable.iv_region_east
    },
    EXTREME_NORD {
        override val display = R.string.region_cm_far_north
        override val description = R.string.region_cm_far_north_description
        override val picture = R.drawable.iv_region_north_far
    },
    LITTORAL {
        override val display = R.string.region_cm_littoral
        override val description = R.string.region_cm_littoral_description
        override val picture = R.drawable.iv_region_littoral
    },
    NORD {
        override val display = R.string.region_cm_north
        override val description = R.string.region_cm_north_description
        override val picture = R.drawable.iv_region_north
    },
    NORD_OUEST {
        override val display = R.string.region_cm_north_west
        override val description = R.string.region_cm_north_west_description
        override val picture = R.drawable.iv_region_north_west
    },
    OUEST {
        override val display = R.string.region_cm_west
        override val description = R.string.region_cm_west_description
        override val picture = R.drawable.iv_region_west
    },
    SUD {
        override val display = R.string.region_cm_south
        override val description = R.string.region_cm_south_description
        override val picture = R.drawable.iv_region_south
    },
    SUD_OUEST {
        override val display = R.string.region_cm_west_south
        override val description = R.string.region_cm_west_south_description
        override val picture = R.drawable.iv_region_south_west
    };

    abstract val display: Int
    abstract val description: Int
    abstract val picture: Int

    override fun toString(): String {
        return "[$display: $description]"
    }

}