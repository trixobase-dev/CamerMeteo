package cm.trixobase.camermeteo.ui.view.town

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Town

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

class TownActivity : ApplicationActivity() {

    private var myRegionNom = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myRegionNom = intent.getStringExtra("region")!!

        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, myRegionNom) },
                    content = {
                        MyContent(
                            Modifier.padding(it),
                            doGetConfigTown(),
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun MyContent(
        modifier: Modifier = Modifier,
        myTown: String,
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Column {
                MyLine()
                MyTowns(myTown)
            }
        }
    }

    @Composable
    private fun MyTowns(myTown: String) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(Town.entries.filter { myRegionNom == it.region }.sortedBy { it.nom }) {
                MyItemTown(myTown, it)
            }
        }

    }

    @Composable
    private fun MyItemTown(myTown: String, town: Town) {
        Card(
            modifier = Modifier.clickable(onClick = { setTownChosen(town.name) }),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RectangleShape),
                    painter = painterResource(id = town.picture),
                    contentDescription = "Image représentant la ville",
                    contentScale = ContentScale.FillWidth
                )
                MyLine(color = Color.LightGray)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.width(180.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = town.nom,
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            modifier = Modifier
                                .size(25.dp)
                                .padding(start = 8.dp),
                            painter = painterResource(id = R.drawable.ic_my_location),
                            contentDescription = "Ville sélectionné",
                            tint = if (myTown == town.name) Color.Black else Color.LightGray
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            //text = "${town.temperature}°",
                            text = "26°",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            //text = "${town.description}°",
                            text = "Partiellement nuageux",
                            fontFamily = FontFamily.SansSerif,
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }

    private fun setTownChosen(townChosen: String) {
        doConfigRegion(Region.entries.filter { myRegionNom == it.nom }[0].name)
        doConfigTown(townChosen)
        showMessage(String.format(getString(R.string.thing_chosen), townChosen))
        onBackPressedDispatcher.onBackPressed()
    }

    @Preview
    @Composable
    private fun TownActivityPreview() {
        CamerMeteoTheme {
            MyContent(myTown = "Garoua")
        }
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun TownActivityDarkPreview() {
        CamerMeteoTheme {
            MyContent(myTown = "Yaoundé")
        }
    }

}