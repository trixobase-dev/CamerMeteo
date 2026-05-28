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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.domain.NotificationWeather
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.region.RegionActivity
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MySubTitle
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.City
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Region
import cm.trixobase.library.common.constants.Temperature
import cm.trixobase.library.common.ui.widget.MyContentError
import cm.trixobase.library.common.ui.widget.MyTextErrorSimple
import cm.trixobase.library.common.ui.widget.RefreshBox
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.delay

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

@Composable
fun Home(openDrawer: () -> Unit, viewModel: HomeViewModel) {
    CamerMeteoTheme {
        MyContent(openDrawer, viewModel)
    }
}

@Composable
private fun MyContent(
    openDrawer: () -> Unit,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current.applicationContext
    val uiStateObserved = viewModel.uiState.observeAsState()
    val state = uiStateObserved.value!!

    val location = if (Utils.process.get(context, AttributeNames.KEY_APP_LOCALISATION_AUTO, false))
        viewModel.location["city"]!! else state.city.display

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTop(viewModel = viewModel, location = location, openDrawer = openDrawer)

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
                        MyContentError(error = this.error) {
                            viewModel.refreshData(context)
                        }
                    else {
                        val scrollState = rememberScrollState()
                        val weather = this.weather!!
                        MyWeatherPicture(weather, viewModel)
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

@Suppress("AssignedValueIsNeverRead")
@Composable
private fun MyTop(
    viewModel: HomeViewModel,
    location: String,
    openDrawer: () -> Unit) {
    val context = LocalContext.current.applicationContext
    var showNotifications by remember { mutableStateOf(false) }

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
                contentDescription = "Image alarm",
                modifier = Modifier.clickable(onClick = { showNotifications = true })
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
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
                    tint = if (Utils.phone.hasInternet(context)) Color.Green else Color.Red
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = location,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_bottom),
                    contentDescription = "Arrow select",
                    tint = MaterialTheme.colorScheme.onSurface
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
        viewModel,
        showNotifications,
        onDismiss = { showNotifications = false }
    )
}

@Composable
private fun MyWeatherPicture(weather: HomeUiWeather, viewModel: HomeViewModel) {
    val context = LocalContext.current.applicationContext
    var hour by remember { mutableStateOf( Utils.time.getCurrentHour()) }
    LaunchedEffect(key1 = hour) {
        while (true) {
            delay(6000)
            hour = Utils.time.getCurrentHour()
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().height(180.dp).padding(vertical = 15.dp)
    ) {
        RefreshBox {
            viewModel.refreshData(context)
        }
        MySection(hour)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            MyLine(Modifier.width(30.dp).height(3.dp))
            Image(
                modifier = Modifier.size(160.dp).padding(top = 35.dp),
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
            .padding(bottom = 5.dp),
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
                modifier= Modifier
                    .width(230.dp)
                    .align(Alignment.BottomEnd),
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
                MyItemWeatherHour(temp = temp)
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
                modifier = Modifier.width(180.dp),
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
                modifier = Modifier.width(140.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyNotifications(
    viewModel: HomeViewModel,
    showNotifications: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val notificationState = viewModel.notifications.observeAsState()
    val notifications = notificationState.value?: listOf()
    viewModel.getNotificationData(context)

    if (showNotifications) {
        ModalBottomSheet(
            modifier = Modifier.wrapContentHeight(),
            sheetState = sheetState,
            onDismissRequest = { onDismiss() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.iv_icon_cloche),
                    contentDescription = "Icon alarm"
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = context.getString(R.string.notifications),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            val notes = mutableListOf<NotificationWeather>()
            notifications.forEach { name ->
                val filtersNote = NotificationWeather.entries.filter { name == it.name }
                if (filtersNote.isNotEmpty())
                    notes.add(filtersNote[0])
            }

            if (notes.isNotEmpty())
                notes.forEach { notification ->
                    MyItemNotification(notification)
                }
            else MyTextErrorSimple(context.getString(R.string.warning_empty_notification))
        }
    }
}

@Composable
private fun MyItemNotification(notification: NotificationWeather) {
    val context = LocalContext.current.applicationContext

    Column (
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalAlignment = Alignment.Start,
    )  {
        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(10.dp),
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "Circle",
                    tint = Color(0xFF046E1E)
                )
            }
            Column(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = if ("sun" == notification.type) R.drawable.iv_icon_sun else R.drawable.iv_icon_rain),
                        contentDescription = "Rain icon"
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = context.getString(notification.title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.akt))
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
                    text = context.getString(notification.content),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily(Font(R.font.inter)),
                )
            }
        }
        MyLine(color = Color.LightGray)
    }
}

@Composable
private fun MyItemWeatherHour(temp: HomeUiWeatherHour) {
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    val weather = ApplicationManager.getWeatherDemo()
    val state = HomeUiState(
        language = Language.FRENCH,
        region = Region.CENTRE,
        city = City.YAOUNDE,
        temperature = Temperature.CELSIUS,
    )
    state.update(weather)
    CamerMeteoTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //MyTop(state = state, location = state.city.display) { }
                //MyWeatherPicture(state.weather)
                //MyWeatherDegree(state.weather)
                //MyOverview(state.weather)
                //MyWeatherHours(state.weather)
//                MyWeatherShare(HomeUiWeather(
//                    apiResult = weather,
//                    temperature = Temperature.CELSIUS
//                ))
            }
        }
    }
}

