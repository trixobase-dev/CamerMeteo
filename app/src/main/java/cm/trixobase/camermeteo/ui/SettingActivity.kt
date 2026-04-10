package cm.trixobase.camermeteo.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class SettingActivity : ApplicationActivity() {

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
        ) {
            Scaffold(
                topBar = { MyToolbar(onBackPressedDispatcher, "Paramètres") },
                content = { MyBody(Modifier.padding(it)) }
            )
        }
    }

    @Composable
    fun MyBody(modifier: Modifier) {
        val songIsOn = remember { mutableStateOf(true) }
        val autoRefreshIsOn = remember { mutableStateOf(true) }
        Surface(
            modifier = modifier.fillMaxSize(),
            contentColor = Color.White
        ) {
            Column {
                MyLine(Color.White)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Unité de température",
                            textAlign = TextAlign.Start
                        )
                        Row(
                            modifier = Modifier.clickable(onClick = { doClickTemperature() }),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "°C",
                                color = Color.LightGray,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                tint = Color.LightGray,
                                contentDescription = "Temperature unit"
                            )
                        }
                    }
                    MyLine()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tonalités de l\'application",
                            textAlign = TextAlign.Start
                        )
                        Switch(
                            checked = songIsOn.value,
                            onCheckedChange = {
                                songIsOn.value = it
                                doClickSongApp(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        )
                    }
                    MyLine()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Actualisation automatique",
                            textAlign = TextAlign.Start
                        )
                        Switch(
                            checked = autoRefreshIsOn.value,
                            onCheckedChange = {
                                autoRefreshIsOn.value = it
                                doClickRefreshApp(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        )
                    }
                    MyLine()
                }
            }
        }
    }

    private fun doClickTemperature() {
        showAvailableSoon()
    }

    private fun doClickSongApp(isOn: Boolean) {
        doConfigSong(isOn)
    }

    private fun doClickRefreshApp(isOn: Boolean) {
        doConfigRefreshAuto(isOn)
    }

    @Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun GreetingPreview() {
        CamerMeteoTheme {
            MyContent()
        }
    }
}