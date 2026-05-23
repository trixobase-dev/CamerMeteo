package cm.trixobase.camermeteo.ui.view.home

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cm.trixobase.camermeteo.ApplicationManager
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.region.RegionActivity
import cm.trixobase.camermeteo.ui.viewui.UiTemp
import cm.trixobase.camermeteo.ui.widget.MySubTitle
import cm.trixobase.camermeteo.ui.widget.MyTextError
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.data.model.Notification
import cm.trixobase.library.common.ui.widget.ToastBox
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.delay

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
    val context = LocalContext.current.applicationContext
    val scrollState = rememberScrollState()
    val uiStateObserved = viewModel.uiState.observeAsState()
    val state = uiStateObserved.value!!

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTop(city = state.city, notifications = state.notifications, openDrawer = action)

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 25.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant)
                if (state.isDemo)
                    viewModel.getWeatherDemo(context)
                else viewModel.getWeatherData(context)
            } else
                state.apply {
                    if (!this.error.isEmpty())
                        MyTextError(error = this.error)
                    else {
                        val weather = this.weather!!
                        MyWeatherPicture(weather)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            MyWeatherDegree(weather)
                            MyOverview(weather)
                            MyWeatherHours(weather)
                            MyWeatherShare(weather)
                        }
                    }
                }
        }
    }
}

@Composable
private fun MyTop(city: City, notifications: List<Notification>, openDrawer: () -> Unit) {
    val context = LocalContext.current.applicationContext
    var showNotifications by remember { mutableStateOf(false) }
    val isConnected = Utils.phone.hasInternet(context)
    val colorButton = MaterialTheme.colorScheme.secondaryContainer
    val colorTitle = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                    //showNotifications = true
                    ToastBox.builder(context).showSoonMessage()
                }))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorButton
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
                    tint = if (isConnected) Color.Green else Color.Red
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = city.display,
                    fontSize = 22.sp,
                    color = colorTitle
                )
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_bottom),
                    contentDescription = "Arrow select",
                    tint = colorTitle
                )
            }
            Image(
                painter = painterResource(id = R.drawable.iv_icon_setting),
                contentDescription = "Paramètres",
                modifier = Modifier.clickable(onClick = { openDrawer() })
            )
        }

    }

    MyNotifications(
        showNotifications,
        notifications,
        onDismiss = { showNotifications = false }
    )
}

@Composable
private fun MyWeatherPicture(weather: HomeUiWeather) {
    var hour by remember { mutableStateOf( Utils.time.getCurrentHour()) }
    LaunchedEffect(key1 = hour) {
        while (true) {
            delay(6000)
            hour = Utils.time.getCurrentHour()
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        MySection(hour)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = weather.getMainPicture()),
                contentDescription = "Weather day"
            )
        }
    }
}

@Composable
private fun MyWeatherDegree(weather: HomeUiWeather) {
    val comic = FontFamily(Font(R.font.comic))
    val colorWhite = MaterialTheme.colorScheme.onSurface
    val colorSoft = MaterialTheme.colorScheme.secondaryContainer

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = weather.getTemperatureMain(),
                fontSize = 75.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = comic,
                textAlign = TextAlign.Center,
                color = colorWhite)
            Text(
                modifier= Modifier.width(230.dp).align(Alignment.BottomEnd),
                text = weather.getUnity(),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = comic,
                textAlign = TextAlign.End,
                color = colorWhite)
        }
        Text(
            text = weather.getDescription(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            fontFamily = comic,
            textAlign = TextAlign.Center,
            color = colorWhite,
            style = MaterialTheme.typography.labelMedium,)
        Text(
            text = weather.getTemperatureInterval(),
            fontSize = 18.sp,
            fontFamily = comic,
            textAlign = TextAlign.Center,
            color = colorSoft)
    }
}

@Composable
private fun MyOverview(weather: HomeUiWeather) {
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier.width(130.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.iv_icon_goutte),
                        contentDescription = "Icon rain"
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = weather.getHumidity(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 13.sp,
                        text = context.getString(R.string.humidity).uppercase(),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Card(
                modifier = Modifier.width(130.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.iv_icon_wind),
                        contentDescription = "Map icon"
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = weather.getWind(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 13.sp,
                        text = context.getString(R.string.wind).uppercase(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier.width(130.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.iv_icon_temperature),
                        contentDescription = "Icon pressure"
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = weather.getPressure(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 13.sp,
                        text = context.getString(R.string.pressure).uppercase(),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Card(
                modifier = Modifier.width(130.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = R.drawable.iv_icon_eye),
                        contentDescription = "Eye icon"
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = weather.getVisibility(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 13.sp,
                        text = context.getString(R.string.visibility).uppercase(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
private fun MyWeatherHours(weather: HomeUiWeather) {
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        MySection(context.getString(R.string.today))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, bottom = 20.dp, top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(weather.getDetails()) { temp ->
                MyItemHour(temp = temp)
            }
        }
    }
}

@Composable
private fun MyWeatherShare(weather: HomeUiWeather) {
    val context = LocalContext.current.applicationContext
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, bottom = 25.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier =Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = context.getString(R.string.share_weather),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = context.getString(R.string.share_weather_friends),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                )
            }
            Button(
                modifier = Modifier.padding(start = 15.dp),
                onClick = { Utils.phone.shareText(context, ApplicationManager.getWeatherToShare(context, weather.apiResult)) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.onPrimary,
                    contentColor = colors.primary
                )
            ) {
                Text(text = context.getString(R.string.share))
            }
        }
    }

}

@Composable
private fun MyItemHour(temp: UiTemp) {
    Card(
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .width(65.dp)
                .padding(vertical = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = temp.hourToDisplay,
                fontSize = 13.sp,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelSmall,
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 10.dp),
                painter = painterResource(id = temp.picture),
                contentDescription = "Icon temperature"
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "${temp.temperature}°",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
private fun MySection(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .height(15.dp)
                .width(2.dp)
                .background(Color.Green)
        )
        MySubTitle(subTitle = title, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
private fun MyNotifications(
    showNotifications: Boolean,
    notifications: List<Notification>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val colors = MaterialTheme.colorScheme

    if (showNotifications) {

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    val weather = HomeUiWeather.builder(
        apiResult = ApplicationManager.getWeatherDemo(),
        temperature = Temperature.CELSIUS)
        .build()
    CamerMeteoTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyTop(city = City.GAROUA_BOULAI, notifications = listOf()) { }
                MyWeatherPicture(weather)
                MyWeatherDegree(weather)
                //MyOverview(weather)
                MyWeatherHours(weather)
                MyWeatherShare(weather)
            }
        }
    }
}

