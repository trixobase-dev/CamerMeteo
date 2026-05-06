package cm.trixobase.camermeteo.ui.view.region

import android.content.Intent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import cm.trixobase.camermeteo.ui.view.town.TownActivity
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.Region

/*
 * Powered by Trixobase Enterprise on 01/05/26
 */

class RegionActivity : ApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(R.string.your_region)) },
                    content = {
                        MyContent(
                            Modifier.padding(it)
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun MyContent(
        modifier: Modifier = Modifier,
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Column {
                MyLine()
                MyRegions()
            }
        }
    }

    @Composable
    private fun MyRegions() {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 15.dp,
            userScrollEnabled =  true
        ) {
            items(Region.entries.sortedBy { it.nom }) {
                MyItemRegion(it)
            }
        }

    }

    @Composable
    private fun MyItemRegion(region: Region) {
        Card(
            modifier = Modifier.clickable(onClick = { goToTown(region.nom) }),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier.width(140.dp).padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier= Modifier. fillMaxWidth(),
                        text = region.nom,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontSize = 19.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = region.picture),
                    contentDescription = "Image représentant la ville",
                    contentScale = ContentScale.FillBounds
                ) }
        }
    }

    private fun goToTown(regionNom: String) {
        val intent = Intent(applicationContext, TownActivity::class.java)
        intent.putExtra("region", regionNom)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
        device = "spec:width=360dp,height=806dp,dpi=320"
    )
    @Composable
    private fun RegionDarkPreview() {
        CamerMeteoTheme {
            MyItemRegion(region = Region.EXTREME_NORD)
        }
    }

}