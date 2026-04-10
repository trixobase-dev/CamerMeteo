package cm.trixobase.camermeteo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import cm.trixobase.camermeteo.ui.domain.UiTown
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

class TownActivity : GlobalActivity() {

    private lateinit var towns: List<UiTown>
    private var myTown = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                MyContent(doGetTownChosen())
            }
        }
    }

    @Composable
    private fun MyContent(townChosen: String) {
        towns = UiTown.getAll()
        townChosen.also { myTown = it }
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = { MyToolbar(onBackPressedDispatcher, "Votre ville") },
                content = { MyBody(Modifier.padding(it)) }
            )
        }
    }

    @Composable
    fun MyBody(modifier: Modifier) {
        Surface(
            modifier = modifier.fillMaxSize(),
            contentColor = Color.White
        ) {
            Column {
                MyLine(Color.White)
                MyTowns()
            }
        }
    }

    @Composable
    fun MyTowns() {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
        ) {
            items(towns.sortedBy { it.name }) { town ->
                MyItemTown(town)
            }
        }
    }

    @Composable
    fun MyItemTown(town: UiTown) {
        Card(
            modifier = Modifier.clickable(onClick = { doSetTownChosen(town.name) }),
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
                        modifier = Modifier,
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
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ville sélectionné",
                            tint = if (myTown == town.name) Color.Black else Color.LightGray
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
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

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun Preview() {
        CamerMeteoTheme {
            MyContent("Yaoundé")
        }
    }

}