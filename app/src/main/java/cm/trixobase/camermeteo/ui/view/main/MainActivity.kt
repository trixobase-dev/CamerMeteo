package cm.trixobase.camermeteo.ui.view.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.SettingActivity
import cm.trixobase.camermeteo.ui.view.TownActivity
import cm.trixobase.camermeteo.ui.viewui.UiDate
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyTextError
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ApplicationActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                MyContent()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyTown(this)
    }

    @Composable
    fun MyContent() {
        val uiState = viewModel.uiState.observeAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyTop(uiState.value?.city ?: "")

                if (uiState.value?.isLoading == true) {
                    CircularProgressIndicator(Modifier.padding(top = 25.dp))
                    viewModel.getWeatherDemo()
                } else {
                    if (uiState.value?.temps?.isEmpty()?: true) {
                        uiState.value?.error.let {
                            MyTextError(error = getString(R.string.warning_internet_connection))
                        }
                    } else
                        uiState.value?.temps?.let { tempsHour ->
                            MyDegree()
                            MyTemp(tempsHour)
                        }
                }
            }
        }
    }

    @Composable
    private fun MyTop(city: String) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painterResource(R.drawable.ic_town),
                contentDescription = "Ville",
                modifier = Modifier.clickable(onClick = {
                    val intent = Intent(applicationContext, TownActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(intent)
                })
            )
            Text(
                text = city, fontSize = 22.sp
            )
            Icon(
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = "Paramètres",
                modifier = Modifier.clickable(onClick = {
                    val intent = Intent(applicationContext, SettingActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(intent)
                })
            )
        }
    }

    @Composable
    private fun MyDegree() {
        val comic = FontFamily(Font(R.font.comic))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

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
                fontFamily = comic,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nuageux°",
                fontSize = 23.sp,
                fontWeight = FontWeight.Light,
                fontFamily = comic,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "30° / 24°",
                fontSize = 18.sp,
                fontFamily = comic,
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
    fun MyTemp(tempsHour: List<UiTemp>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            MyLine()
            MyTempsByHour(tempsHour)
            MyLine()
            MyTempsByDate()
        }
    }

    @Composable
    fun MyTempsByHour(tempsHour: List<UiTemp>) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            items(tempsHour) { temp ->
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

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
            MyContent()
        }
    }

}