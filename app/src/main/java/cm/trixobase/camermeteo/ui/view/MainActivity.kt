package cm.trixobase.camermeteo.ui.view

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.R
import cm.trixobase.camermeteo.common.widget.MyLine
import cm.trixobase.camermeteo.ui.ApplicationActivity
import cm.trixobase.camermeteo.ui.UiDate
import cm.trixobase.camermeteo.ui.UiTemp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                MyContent(doGetConfigTown())
            }
        }
    }

    @Composable
    fun MyContent(townChosen: String) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //contentColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyTop(townChosen)
                MyDegree()
                MyTemp()
            }
        }
    }

    @Composable
    private fun MyTop(townChosen: String) {
        var myTown by remember { mutableStateOf(townChosen) }

        Row(
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painterResource(R.drawable.ic_town),
                contentDescription = "Ville",
                modifier = Modifier.clickable(onClick = { goToTownActivity() })
            )
            Text(
                text = myTown, fontSize = 22.sp
            )
            Icon(
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = "Paramètres",
                modifier = Modifier.clickable(onClick = { goToSettingActivity() })
            )/*
            Image(
                modifier = Modifier.size(25.dp)
                    .padding(2.dp)                      // retrait pour la bordure
                    .clip(CircleShape)                  // form image
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Paramètres",
                contentScale = ContentScale.Crop,      //remplissage de l'image
            )
            */
        }
    }

    @Suppress("SpellCheckingInspection")
    @Composable
    private fun MyDegree() {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Text(
                text = "34°",
                fontSize = 75.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nuageux°",
                fontSize = 23.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
            Text(
                text = "30° / 24°",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )
        }
    }

    @Composable
    fun MyTemp() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            MyLine()
            MyTempsByHour()
            MyLine()
            MyTempsByDate()
        }
    }

    @Composable
    fun MyTempsByHour() {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            items(UiTemp.getAll()) { temp ->
                MyItemHour(temp = temp)
            }
        }
    }

    @Composable
    fun MyTempsByDate() {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(UiDate.getAll()) { date ->
                MyItemDate(date = date)
            }
        }
    }

    @Composable
    fun MyItemHour(modifier: Modifier = Modifier, temp: UiTemp) {
        Column(
            modifier = modifier
                .width(55.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = modifier.height(25.dp), text = temp.hourToDisplay, fontSize = 10.sp
            )
            Image(
                modifier = modifier.size(22.dp),
                painter = painterResource(id = temp.picture),
                contentDescription = "Icon temperature"
            )
            Text(
                modifier = modifier,
                text = "${temp.temperature}°",
                fontSize = 11.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }

    @Composable
    fun MyItemDate(modifier: Modifier = Modifier, date: UiDate) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.width(110.dp),
                text = date.dateToDisplay,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = date.picture),
                contentDescription = "Icon temperature"
            )
            Text(
                text = date.temperatureToDisplay,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
        MyLine()
    }

    @Preview
    @Composable
    private fun PreviewTheme() {
        CamerMeteoTheme {
            MyContent("Limbé")
        }
    }

    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
            MyContent("Yaoundé")
            /*
            MyItemDate(Modifier, UiDate.builder()
                    .withDate(Calendar.getInstance())
                    .withTemperature(23))
             */
        }
    }

    private fun goToTownActivity() {
        val intent = Intent(applicationContext, TownActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }

    private fun goToSettingActivity() {
        val intent = Intent(applicationContext, SettingActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }
}