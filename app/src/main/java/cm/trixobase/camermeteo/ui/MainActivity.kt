package cm.trixobase.camermeteo.ui

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.camermeteo.ui.domain.UiDate
import cm.trixobase.camermeteo.ui.domain.UiTemp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : GlobalActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                MyContent()
            }
        }
    }

    @Composable
    private fun MyContent() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            contentColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                MyTop()
                MyDegree()
                MyBody()
            }
        }
    }

    @Composable
    private fun MyTop() {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Ville",
                modifier = Modifier.clickable(onClick = { goToTownActivity() })
            )
            Text(
                text = doGetTownChosen(),
                fontSize = 18.sp
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Paramètres",
                modifier = Modifier.clickable(onClick = { goToSettingActivity() })
            )
            /*
            Image(
                modifier = Modifier.size(25.dp)
                    .padding(2.dp)                      // retrait pour la bordure
                    .clip(CircleShape)                  // forme de l'image
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
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
        }
    }

    @Composable
    fun MyBody() {
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
                MyItemHour(temp)
            }
        }
    }

    @Composable
    fun MyTempsByDate() {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(UiDate.getAll()) { date ->
                MyItemDate(date)
            }
        }
    }

    @Composable
    fun MyItemHour(temp: UiTemp) {
        Column(
            modifier = Modifier
                .width(55.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.height(25.dp),
                text = temp.hour,
                fontSize = 10.sp
            )
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = temp.picture,
                contentDescription = "Icon temperature"
            )
            Text(
                modifier = Modifier,
                text = "${temp.temperature}°",
                fontSize = 11.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }

    @Composable
    fun MyItemDate(date: UiDate) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(110.dp),
                text = date.date,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
            Icon(
                imageVector = date.picture,
                contentDescription = "Icon temperature"
            )
            Text(
                text = date.temperature,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
        MyLine()
    }

    @Preview(showBackground = true)
    @Composable
    private fun Preview() {
        CamerMeteoTheme {
            MyToolbar(onBackPressedDispatcher, "Your town")
        }
    }

    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
            MyContent()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(onBackPressedDispatcher: OnBackPressedDispatcher, title: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 5.dp)
                        .clickable(onClick = { onBackPressedDispatcher.onBackPressed() }),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour"
                )
                Text(title)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White),
    )
}

@Composable
fun MyLine(color: Color = Color.Gray) {
    Spacer(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color)
    )
}