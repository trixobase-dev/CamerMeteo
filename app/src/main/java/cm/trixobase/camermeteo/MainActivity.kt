package cm.trixobase.camermeteo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
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
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CamerMeteoTheme {
                MyContent()
            }
        }
    }
}

@Composable
fun MyContent() {
    Surface (
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 35.dp, start = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            MyTop()
            MyDegree()
        }
    }
}

@Composable
fun MyTop() {
    Row(
        modifier = Modifier.padding(top = 5.dp, bottom = 50.dp, start = 10.dp, end = 10.dp).fillMaxWidth(),
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

@Composable
fun MyDegree() {
    Column(
        modifier = Modifier.fillMaxWidth(),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CamerMeteoTheme {
        MyContent()
    }
}