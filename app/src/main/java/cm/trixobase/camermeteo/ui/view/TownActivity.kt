package cm.trixobase.camermeteo.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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

import cm.trixobase.camermeteo.R
import cm.trixobase.camermeteo.common.widget.MyLine
import cm.trixobase.camermeteo.common.widget.MyToolbar
import cm.trixobase.camermeteo.ui.ApplicationActivity
import cm.trixobase.camermeteo.ui.UiTown
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

class TownActivity : ApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(R.string.your_town)) },
                    content = {
                        MyContent(
                            Modifier.padding(it),
                            doGetConfigTown(),
                            UiTown.getAll()
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
        towns: List<UiTown> = UiTown.getAll()
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
            //contentColor = Color.White
        ) {
            Column {
                MyLine(Color.White)
                MyTowns(myTown, towns)
            }
        }
    }

    @Composable
    fun MyTowns(myTown: String, towns: List<UiTown>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(towns.sortedBy { it.name }) {
                MyItemTown(myTown, it)
            }
        }
    }

    @Composable
    fun MyItemTown(myTown: String, town: UiTown) {
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
                MyLine()
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
                            text = town.name,
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
                            text = "${town.temperature}°",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${town.description}°",
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
        doConfigTown(townChosen)
        showMessage(String.format(getString(R.string.town_chosen), townChosen))
        onBackPressedDispatcher.onBackPressed()
    }

    @Preview
    @Composable
    private fun TownActivityPreview() {
        CamerMeteoTheme {
            MyContent(myTown = "Limbé")
        }
    }

    @Preview(uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun TownActivityDarkPreview() {
        CamerMeteoTheme {
            MyContent(myTown = "Yaoundé")
        }
    }

}