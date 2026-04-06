package cm.trixobase.camermeteo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.handwriting.handwritingDetector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import cm.trixobase.camermeteo.ui.theme.SettingActivity

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CamerMeteoTheme {
                MyContent({ MyButton(applicationContext) })
            }
        }
    }
}

@Composable
private fun MyContent(myButton: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            MyTop()
            MyDegree()
            MyBody()
            myButton()
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
            contentDescription = "Ville"
        )
        Text(
            text = "Yaoundé",
            fontSize = 16.sp
        )
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Paramètres"
        )
        /*
        Image(
            modifier = Modifier.size(25.dp).clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Paramètres"
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
            .padding(top = 60.dp, bottom = 50.dp),
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
fun MyLine() {
    Spacer(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Gray)
    )
}

@Composable
fun MyTempsByHour() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        items(UiTemp.getAll()) {temp ->
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
        items(UiDate.getAll()) {date ->
            MyItemDate(date)
        }
    }
}

@Composable
fun MyItemHour(temp: UiTemp) {
    Column(
        modifier = Modifier
            .width(50.dp)
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
            .fillMaxWidth().height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date.date,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace
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

@Composable
private fun MyButton(context: Context) {
    Button(
        modifier = Modifier.padding(top = 35.dp),
        onClick = {
            val intent = Intent(context, SettingActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text(text = "Paramètres")
    }
}

@Composable
private fun MyButtonTest() {
    Button(
        modifier = Modifier.padding(top = 35.dp),
        onClick = {
            Log.d("Tag", "Bouton cliqué")
        }
    ) {
        Text(text = "Prévision")
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    CamerMeteoTheme {
        MyContent({ MyButtonTest() })
        //MyTemp()
    }
}