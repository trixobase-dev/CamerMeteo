package cm.trixobase.camermeteo.ui.view.home

import android.content.Intent
import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.region.RegionActivity
import cm.trixobase.camermeteo.ui.viewui.UiDate
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyTextError
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.Town
import cm.trixobase.library.common.ui.widget.ToastBox

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

@Composable
fun Home(action: () -> Unit, viewModel: HomeViewModel) {
    CamerMeteoTheme {
        MyContent(action, viewModel)
    }
}

@Composable
private fun MyContent(
    action: () -> Unit,
    viewModel: HomeViewModel
) {
    val scrollState = rememberScrollState()
    val uiStateObserved = viewModel.uiState.observeAsState()
    val uiState = uiStateObserved.value!!

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTop(city = uiState.city, openDrawer = action)

            if (uiState.isLoading) {
                CircularProgressIndicator(Modifier.padding(top = 25.dp))
                if (uiState.isDemo)
                    viewModel.getWeatherDemo()
                else viewModel.getWeatherData()
            } else
                uiState.apply {
                    if (!this.error.isEmpty())
                        MyTextError(error = "${LocalContext.current.applicationContext.getString(R.string.warning_internet_connection)}\n${this.error}")
                    else {
                        MyDegree()
                        MyTemp(this.temps)
                    }
                }
        }
    }
}

@Composable
private fun MyTop(city: String, openDrawer: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val colorWhite = MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painterResource(id = R.drawable.iv_icon_cloche),
            contentDescription = "Notification",
            modifier = Modifier.clickable(onClick = {
                ToastBox.builder(context).withMessage(context.getString(R.string.warning_available_soon))
                    .showLong()
            })
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier,
            onClick = {
                val intent = Intent(context, RegionActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        ) {
            Icon(
                modifier = Modifier.size(8.dp),
                painter = painterResource(id = R.drawable.ic_circle),
                contentDescription = "Circle",
                tint = Color.Green)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = Town.entries.filter { city == it.name }[0].nom,
                fontSize = 22.sp,
                color = colorWhite
            )
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = R.drawable.ic_arrow_bottom),
                contentDescription = "Arrow select",
                tint = MaterialTheme.colorScheme.onSurface)
        }
        Image(
            painter = painterResource(id = R.drawable.iv_icon_setting),
            contentDescription = "Paramètres",
            modifier = Modifier.clickable(onClick = { openDrawer() })
        )
    }
}

@Composable
private fun MyDegree() {
    val comic = FontFamily(Font(R.font.comic))
    val colorWhite = MaterialTheme.colorScheme.onSurface
    val colorSoft = MaterialTheme.colorScheme.secondaryContainer

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Image(
            modifier = Modifier.size(110.dp),
            painter = painterResource(id = cm.trixobase.camermeteo.R.drawable.iv_meteo_sun_nuage),
            contentDescription = "Weather day"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Text(
            text = "34°",
            fontSize = 75.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = comic,
            textAlign = TextAlign.Center,
            color = colorWhite
        )
        Text(
            text = "Nuageux°",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            fontFamily = comic,
            textAlign = TextAlign.Center,
            color = colorWhite
        )
        Text(
            text = "30° / 24°",
            fontSize = 18.sp,
            fontFamily = comic,
            textAlign = TextAlign.Center,
            color = colorSoft
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Composable
private fun MyTemp(tempsHour: List<UiTemp>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        MyLine(color = Color.LightGray)
        MyTempsByHour(tempsHour)
        MyLine(color = Color.LightGray)
        MyTempsByDate()
    }
}

@Composable
private fun MyTempsByHour(tempsHour: List<UiTemp>) {
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
private fun MyTempsByDate() {
    UiDate.getAll().forEach { date ->
        MyItemDate(date = date)
    }
}

@Composable
private fun MyItemHour(modifier: Modifier = Modifier, temp: UiTemp) {
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
private fun MyItemDate(modifier: Modifier = Modifier, date: UiDate) {
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
    MyLine(color = Color.LightGray)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    CamerMeteoTheme {
        Surface {
            MyTop(city = Town.GAROUA_BOULAI.name) { }
        }
    }
}

