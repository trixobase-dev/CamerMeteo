package cm.trixobase.camermeteo.ui.view.town

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.MainActivity
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Region

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

class CityActivity : ApplicationActivity() {

    private var region = Region.CENTRE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myNameRegion = intent.getStringExtra("region")!!
        region = Region.entries.filter { myNameRegion == it.name }[0]

        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(region.display)) },
                    content = {
                        MyContent(
                            Modifier.padding(it),
                            doGetConfigCity(),
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
                MyCities(myTown)
            }
        }
    }

    @Composable
    private fun MyCities(myTown: String) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalItemSpacing = 15.dp,
            userScrollEnabled = true
        ) {
            items(City.entries.filter { region.display == it.region }.sortedBy { it.display }) {
                MyItemCity(myTown, it)
            }
        }
    }

    @Composable
    private fun MyItemCity(myCity: String, city: City) {
        Card(
            modifier = Modifier.clickable(onClick = { setCity(city) }),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .width(155.dp)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = city.display,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        modifier = Modifier.size(25.dp).padding(start = 8.dp),
                        painter = painterResource(id = R.drawable.ic_my_location),
                        contentDescription = "Icon target",
                        tint = if (myCity == city.name)
                            MaterialTheme.colorScheme.inverseSurface
                        else MaterialTheme.colorScheme.surface)
                }
                Image(
                    modifier = Modifier.fillMaxWidth().height(120.dp).border(1.dp, Color.Gray, RoundedCornerShape(4.dp)).padding(1.dp),
                    painter = painterResource(id = city.picture),
                    contentDescription = "Image city",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

    private fun setCity(city: City) {
        doConfigRegion(region)
        doConfigCity(city)
        showMessage(String.format(getString(R.string.thing_chosen), city.display))
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
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