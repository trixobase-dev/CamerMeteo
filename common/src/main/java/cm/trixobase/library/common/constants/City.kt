package cm.trixobase.library.common.constants

import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 27/04/26
 */

enum class City {
    ABONG_MBANG {
        override val region = Region.EST.display
        override val display = "Abong-Mbang"
        override val lat = "3.9896"
        override val lon = "13.1739"
        override val picture = R.drawable.iv_city_east_abong_mbang
    },
    AKOM_II {
        override val region = Region.SUD.display
        override val display = "Akom II"
        override val lat = "2.8000"
        override val lon = "10.5667"
        override val picture = R.drawable.iv_city_south_akom
    },
    AKONOLINGA {
        override val region = Region.CENTRE.display
        override val display = "Akonolinga"
        override val lat = "3.7737"
        override val lon = "12.2449"
        override val picture = R.drawable.iv_city_center_akonolinga
    },
    AKWAYA {
        override val region = Region.SUD_OUEST.display
        override val display = "Akwaya"
        override val lat = "6.1667"
        override val lon = "9.4500"
        override val picture = R.drawable.iv_town_yaounde
    },
    AMBAM {
        override val region = Region.SUD.display
        override val display = "Ambam"
        override val lat = "2.3860"
        override val lon = "11.2722"
        override val picture = R.drawable.iv_city_south_ambam
    },
    BAFANG {
        override val region = Region.OUEST.display
        override val display = "Bafang"
        override val lat = "5.1500"
        override val lon = "10.1833"
        override val picture = R.drawable.iv_city_west_bafang
    },
    BAFIA {
        override val region = Region.CENTRE.display
        override val display = "Bafia"
        override val lat = "4.7399"
        override val lon = "11.2206"
        override val picture = R.drawable.iv_city_center_bafia
    },
    BAFOUSSAM {
        override val region = Region.OUEST.display
        override val display = "Bafoussam"
        override val lat = "5.4667"
        override val lon = "10.4167"
        override val picture = R.drawable.iv_city_west_bafoussam
    },
    BAHAM {
        override val region = Region.OUEST.display
        override val display = "Baham"
        override val lat = "5.3833"
        override val lon = "10.3833"
        override val picture = R.drawable.iv_city_west_baham
    },
    BALI {
        override val region = Region.NORD_OUEST.display
        override val display = "Bali"
        override val lat = "5.9000"
        override val lon = "10.0167"
        override val picture = R.drawable.iv_city_north_west_bali
    },
    BAMENDA {
        override val region = Region.NORD_OUEST.display
        override val display = "Bamenda"
        override val lat = "5.9614"
        override val lon = "10.1517"
        override val picture = R.drawable.iv_town_yaounde
    },
    BANGANGTE {
        override val region = Region.OUEST.display
        override val display = "Bangangté"
        override val lat = "5.1500"
        override val lon = "10.5333"
        override val picture = R.drawable.iv_city_west_bangangte
    },
    BANYO {
        override val region = Region.ADAMAOUA.display
        override val display = "Banyo"
        override val lat = "6.7485"
        override val lon = "11.8045"
        override val picture = R.drawable.iv_city_adamaoua_banyo
    },
    BATOURI {
        override val region = Region.EST.display
        override val display = "Batouri"
        override val lat = "4.4358"
        override val lon = "14.3645"
        override val picture = R.drawable.iv_city_east_batouri
    },
    BERTOUA {
        override val region = Region.EST.display
        override val display = "Bertoua"
        override val lat = "4.5777"
        override val lon = "13.6844"
        override val picture = R.drawable.iv_city_east_bertoua
    },
    BOGO {
        override val region = Region.EXTREME_NORD.display
        override val display = "Bogo"
        override val lat = "10.7351"
        override val lon = "14.6099"
        override val picture = R.drawable.iv_city_north_far_bogo
    },
    BUEA {
        override val region = Region.SUD_OUEST.display
        override val display = "Buéa"
        override val lat = "4.1568"
        override val lon = "9.2323"
        override val picture = R.drawable.iv_city_south_west_buea
    },
    DOUALA {
        override val region = Region.LITTORAL.display
        override val display = "Douala"
        override val lat = "4.0500"
        override val lon = "9.7000"
        override val picture = R.drawable.iv_city_littoral_douala
    },
    DOUME {
        override val region = Region.EST.display
        override val display = "Doumé"
        override val lat = "4.2500"
        override val lon = "13.4333"
        override val picture = R.drawable.iv_city_east_doume
    },
    DSCHANG {
        override val region = Region.OUEST.display
        override val display = "Dschang"
        override val lat = "5.4500"
        override val lon = "10.0500"
        override val picture = R.drawable.iv_city_west_dschang
    },
    EBOLOWA {
        override val region = Region.SUD.display
        override val display = "Ebolowa"
        override val lat = "2.9167"
        override val lon = "11.1500"
        override val picture = R.drawable.iv_city_south_ebolowa
    },
    EDEA {
        override val region = Region.LITTORAL.display
        override val display = "Edéa"
        override val lat = "3.8000"
        override val lon = "10.1333"
        override val picture = R.drawable.iv_city_littoral_edea
    },
    ESEKA {
        override val region = Region.CENTRE.display
        override val display = "Eséka"
        override val lat = "3.5700"
        override val lon = "10.7700"
        override val picture = R.drawable.iv_city_center_eseka
    },
    FONTEM {
        override val region = Region.NORD_OUEST.display
        override val display = "Fontem"
        override val lat = "5.6667"
        override val lon = "9.9167"
        override val picture = R.drawable.iv_city_north_west_fontem
    },
    FOUMBAN {
        override val region = Region.OUEST.display
        override val display = "Foumban"
        override val lat = "5.7333"
        override val lon = "10.9000"
        override val picture = R.drawable.iv_city_west_foumban
    },
    FOUMBOT {
        override val region = Region.OUEST.display
        override val display = "Foumbot"
        override val lat = "5.5000"
        override val lon = "10.6833"
        override val picture = R.drawable.iv_city_west_foumbot
    },
    FUNDONG {
        override val region = Region.NORD_OUEST.display
        override val display = "Fundong"
        override val lat = "6.2833"
        override val lon = "10.3667"
        override val picture = R.drawable.iv_city_north_west_fundong
    },
    GAROUA {
        override val region = Region.NORD.display
        override val display = "Garoua"
        override val lat = "9.3000"
        override val lon = "13.4000"
        override val picture = R.drawable.iv_city_north_garoua
    },
    GAROUA_BOULAI {
        override val region = Region.EST.display
        override val display = "Garoua-Boulaï"
        override val lat = "5.9000"
        override val lon = "15.0000"
        override val picture = R.drawable.iv_city_east_garoua_boulai
    },
    GUIDER {
        override val region = Region.NORD.display
        override val display = "Guider"
        override val lat = "9.8833"
        override val lon = "13.9500"
        override val picture = R.drawable.iv_city_north_guider
    },
    KAELE {
        override val region = Region.EXTREME_NORD.display
        override val display = "Kaélé"
        override val lat = "10.1000"
        override val lon = "14.4500"
        override val picture = R.drawable.iv_city_north_far_kaele
    },
    KOUSSERI {
        override val region = Region.EXTREME_NORD.display
        override val display = "Kousséri"
        override val lat = "12.0833"
        override val lon = "14.1333"
        override val picture = R.drawable.iv_city_north_far_kousseri
    },
    KRIBI {
        override val region = Region.SUD.display
        override val display = "Kribi"
        override val lat = "2.9333"
        override val lon = "9.9167"
        override val picture = R.drawable.iv_city_south_kribi
    },
    KUMBA {
        override val region = Region.SUD_OUEST.display
        override val display = "Kumba"
        override val lat = "4.6333"
        override val lon = "9.4500"
        override val picture = R.drawable.iv_city_south_west_kumba
    },
    KUMBO {
        override val region = Region.NORD_OUEST.display
        override val display = "Kumbo"
        override val lat = "6.2000"
        override val lon = "10.6667"
        override val picture = R.drawable.iv_city_north_west_kumbo
    },
    LAGDO {
        override val region = Region.NORD.display
        override val display = "Lagdo"
        override val lat = "9.0667"
        override val lon = "13.7333"
        override val picture = R.drawable.iv_city_north_lagdo
    },
    LIMBE {
        override val region = Region.SUD_OUEST.display
        override val display = "Limbé"
        override val lat = "4.0333"
        override val lon = "9.2167"
        override val picture = R.drawable.iv_city_south_west_limbe
    },
    LOLODORF {
        override val region = Region.SUD.display
        override val display = "Lolodorf"
        override val lat = "3.2333"
        override val lon = "10.5667"
        override val picture = R.drawable.iv_city_south_lolodorf
    },
    LOMIE {
        override val region = Region.EST.display
        override val display = "Lomié"
        override val lat = "3.1667"
        override val lon = "13.6167"
        override val picture = R.drawable.iv_town_yaounde
    },
    LOUM {
        override val region = Region.LITTORAL.display
        override val display = "Loum"
        override val lat = "4.8333"
        override val lon = "9.6500"
        override val picture = R.drawable.iv_city_littoral_loum
    },
    MAMFE {
        override val region = Region.SUD_OUEST.display
        override val display = "Mamfé"
        override val lat = "5.7500"
        override val lon = "9.3167"
        override val picture = R.drawable.iv_city_south_west_mamfe
    },
    MANJO {
        override val region = Region.LITTORAL.display
        override val display = "Manjo"
        override val lat = "4.8333"
        override val lon = "9.8167"
        override val picture = R.drawable.iv_city_littoral_manjo
    },
    MAROUA {
        override val region = Region.EXTREME_NORD.display
        override val display = "Maroua"
        override val lat = "10.5906"
        override val lon = "14.3159"
        override val picture = R.drawable.iv_city_north_far_maroua
    },
    MBALMAYO {
        override val region = Region.CENTRE.display
        override val display = "Mbalmayo"
        override val lat = "3.5167"
        override val lon = "11.5000"
        override val picture = R.drawable.iv_city_center_mbalmayo
    },
    MBANGA {
        override val region = Region.LITTORAL.display
        override val display = "Mbanga"
        override val lat = "4.5000"
        override val lon = "9.6500"
        override val picture = R.drawable.iv_city_littoral_mbanga
    },
    MBENGWI {
        override val region = Region.NORD_OUEST.display
        override val display = "Mbengwi"
        override val lat = "5.9833"
        override val lon = "10.0000"
        override val picture = R.drawable.iv_town_yaounde
    },
    MBOUDA {
        override val region = Region.OUEST.display
        override val display = "Mbouda"
        override val lat = "5.6333"
        override val lon = "10.2667"
        override val picture = R.drawable.iv_city_west_mbouda
    },
    MEIGANGA {
        override val region = Region.ADAMAOUA.display
        override val display = "Meïganga"
        override val lat = "6.5133"
        override val lon = "14.2995"
        override val picture = R.drawable.iv_city_adamaoua_meiganga
    },
    MELONG {
        override val region = Region.LITTORAL.display
        override val display = "Melong"
        override val lat = "5.1333"
        override val lon = "9.8833"
        override val picture = R.drawable.iv_city_littoral_melong
    },
    MOKOLO {
        override val region = Region.EXTREME_NORD.display
        override val display = "Mokolo"
        override val lat = "10.7333"
        override val lon = "13.8000"
        override val picture = R.drawable.iv_city_north_far_mokolo
    },
    MORA {
        override val region = Region.EXTREME_NORD.display
        override val display = "Mora"
        override val lat = "11.0500"
        override val lon = "14.4500"
        override val picture = R.drawable.iv_city_north_far_mora
    },
    MUTENGENE {
        override val region = Region.SUD_OUEST.display
        override val display = "Mutengene"
        override val lat = "4.1000"
        override val lon = "9.3500"
        override val picture = R.drawable.iv_city_south_west_mutengene
    },
    MUYUKA {
        override val region = Region.SUD_OUEST.display
        override val display = "Muyuka"
        override val lat = "4.2833"
        override val lon = "9.4000"
        override val picture = R.drawable.iv_city_south_west_muyuka
    },
    NANGA_EBOKO {
        override val region = Region.CENTRE.display
        override val display = "Nanga Eboko"
        override val lat = "4.3667"
        override val lon = "12.3667"
        override val picture = R.drawable.iv_city_center_nanga_eboko
    },
    NKAMBE {
        override val region = Region.NORD_OUEST.display
        override val display = "Nkambé"
        override val lat = "6.5833"
        override val lon = "10.8333"
        override val picture = R.drawable.iv_town_yaounde
    },
    NKONGSAMBA {
        override val region = Region.LITTORAL.display
        override val display = "Nkongsamba"
        override val lat = "4.9500"
        override val lon = "9.9333"
        override val picture = R.drawable.iv_city_littoral_nkongsamba
    },
    NKOTENG {
        override val region = Region.CENTRE.display
        override val display = "Nkoteng"
        override val lat = "4.5167"
        override val lon = "12.0333"
        override val picture = R.drawable.iv_city_center_nkoteng
    },
    NGAOUNDERE {
        override val region = Region.ADAMAOUA.display
        override val display = "Ngaoundéré"
        override val lat = "7.3235"
        override val lon = "13.5756"
        override val picture = R.drawable.iv_city_adamaoua_ngaoundere
    },
    OBALA {
        override val region = Region.CENTRE.display
        override val display = "Obala"
        override val lat = "4.1667"
        override val lon = "11.5333"
        override val picture = R.drawable.iv_city_center_obala
    },
    PENJA {
        override val region = Region.LITTORAL.display
        override val display = "Penja"
        override val lat = "4.5833"
        override val lon = "9.8167"
        override val picture = R.drawable.iv_city_littoral_penja
    },
    POLI {
        override val region = Region.NORD.display
        override val display = "Poli"
        override val lat = "8.4833"
        override val lon = "13.2500"
        override val picture = R.drawable.iv_city_north_poli
    },
    REY_BOUBA {
        override val region = Region.NORD.display
        override val display = "Rey Bouba"
        override val lat = "8.7667"
        override val lon = "14.1667"
        override val picture = R.drawable.iv_city_north_rey_bouba
    },
    SAA {
        override val region = Region.CENTRE.display
        override val display = "Sa\'a"
        override val lat = "4.3667"
        override val lon = "11.4500"
        override val picture = R.drawable.iv_city_center_saa
    },
    SANGMELIMA {
        override val region = Region.SUD.display
        override val display = "Sangmelima"
        override val lat = "2.9333"
        override val lon = "11.9833"
        override val picture = R.drawable.iv_city_south_sangmelima
    },
    TCHOLLIRE {
        override val region = Region.NORD.display
        override val display = "Tcholliré"
        override val lat = "8.4000"
        override val lon = "14.1667"
        override val picture = R.drawable.iv_city_north_tchollire
    },
    TIBATI {
        override val region = Region.ADAMAOUA.display
        override val display = "Tibati"
        override val lat = "6.4667"
        override val lon = "12.6167"
        override val picture = R.drawable.iv_city_adamaoua_tibati
    },
    TIGNERE {
        override val region = Region.ADAMAOUA.display
        override val display = "Tignère"
        override val lat = "7.3667"
        override val lon = "12.6500"
        override val picture = R.drawable.iv_city_adamaoua_tignere
    },
    TIKO {
        override val region = Region.SUD_OUEST.display
        override val display = "Tiko"
        override val lat = "4.1500"
        override val lon = "9.3667"
        override val picture = R.drawable.iv_city_south_west_tiko
    },
    WUM {
        override val region = Region.NORD_OUEST.display
        override val display = "Wum"
        override val lat = "6.5500"
        override val lon = "10.0667"
        override val picture = R.drawable.iv_city_north_west_wum
    },
    YAGOUA {
        override val region = Region.EXTREME_NORD.display
        override val display = "Yagoua"
        override val lat = "10.3333"
        override val lon = "15.2333"
        override val picture = R.drawable.iv_city_north_far_yagoua
    },
    YAOUNDE {
        override val region = Region.CENTRE.display
        override val display = "Yaoundé"
        override val lat = "3.8667"
        override val lon = "11.5167"
        override val picture = R.drawable.iv_city_center_yaounde
    },
    YOKO {
        override val region = Region.CENTRE.display
        override val display = "Yoko"
        override val lat = "5.5333"
        override val lon = "12.3167"
        override val picture = R.drawable.iv_city_center_yoko
    };

    abstract val display: String
    abstract val region: Int
    abstract val lat: String
    abstract val lon: String
    abstract val picture: Int

    override fun toString(): String {
        return "[$display ($region): $lat-$lon]"
    }
}