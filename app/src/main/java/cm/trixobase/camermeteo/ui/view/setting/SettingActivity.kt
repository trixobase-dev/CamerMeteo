@file:Suppress("AssignedValueIsNeverRead")

package cm.trixobase.camermeteo.ui.view.setting

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.data.di.AppModule
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class SettingActivity : ApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(R.string.settings)) },
                    content = {
                        MyContent(
                            Modifier.padding(it),
                            doGetConfigSong(),
                            doGetConfigRefreshAuto(),
                            doGetConfigDemo()
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun MyContent(
        modifier: Modifier = Modifier,
        configSong: Boolean = true,
        configRefreshAuto: Boolean = true,
        configDemo: Boolean = true
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
        ) {
            Column {
                MyLine(color = MaterialTheme.colorScheme.inverseSurface)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    OptionOne()
                    MyLine()
                    OptionTwo(configSong)
                    MyLine()
                    OptionThree(configRefreshAuto)
                    MyLine()
                    OptionFour(configDemo)
                    MyLine()
                }
            }
        }
    }

    @Composable
    private fun OptionOne() {
        var showDialog by remember { mutableStateOf(false) }
        var myUnity by remember { mutableStateOf(doGetConfigTemperatureUnity()) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getString(R.string.temperature_unity),
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.clickable(
                    onClick = { showDialog = true }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = myUnity,
                    color = Color.LightGray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    tint = Color.LightGray,
                    contentDescription = "Temperature unit"
                )
            }
        }

        MyDialog(
            showDialog,
            onConfirm = {
                doConfigTemperatureUnity(unity = it)
                myUnity = it
                showDialog = false
            },
            onDismiss = { showDialog = false },
            onRequestDismiss = { showDialog = false }
        )
    }

    @Composable
    private fun OptionTwo(configSong: Boolean) {
        val songIsOn = remember { mutableStateOf(configSong) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getString(R.string.app_tonalities),
                textAlign = TextAlign.Start
            )
            Switch(
                checked = songIsOn.value,
                onCheckedChange = {
                    songIsOn.value = it
                    doConfigSong(it)
                }
                /*
                ,colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.onSurfaceVariant)
                 */
            )
        }
    }

    @Composable
    private fun OptionThree(configRefreshAuto: Boolean) {
        val autoRefreshIsOn = remember { mutableStateOf(configRefreshAuto) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getString(R.string.refresh_automatic),
                textAlign = TextAlign.Start
            )
            Switch(
                checked = autoRefreshIsOn.value,
                onCheckedChange = {
                    autoRefreshIsOn.value = it
                    doConfigRefreshAuto(it)
                }
            )
        }
    }

    @Composable
    private fun OptionFour(configDemo: Boolean) {
        val demoIsOn = remember { mutableStateOf(configDemo) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mode démo",
                textAlign = TextAlign.Start
            )
            Switch(
                checked = demoIsOn.value,
                onCheckedChange = {
                    demoIsOn.value = it
                    doConfigDemo(it)
                }
            )
        }
    }

    @Composable
    private fun MyDialog(
        showDialog: Boolean,
        onRequestDismiss: () -> Unit,
        onConfirm: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        var selectedOption by rememberSaveable { mutableStateOf(doGetConfigTemperatureUnity()) }
        val colors = MaterialTheme.colorScheme

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { onRequestDismiss() },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = getString(R.string.temperature_unity),
                        textAlign = TextAlign.Center,
                        color = colors.primary
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        MyLine()
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )

                        val listOptions = listOf(
                            AppModule.TEMPERATURE.CELSIUS.unity,
                            AppModule.TEMPERATURE.FAHRENHEIT.unity,
                            AppModule.TEMPERATURE.KELVIN.unity
                        )
                        listOptions.forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(option)
                                RadioButton(
                                    selected = option == selectedOption,
                                    onClick = { selectedOption = option })
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )
                        MyLine()
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { onConfirm(selectedOption) },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text("Ok", color = colors.onPrimary)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.secondary)
                    ) {
                        Text(text = getString(R.string.cancel), color = colors.onSecondary)
                    }
                }
            )
        }
    }

    @Preview
    @Composable
    private fun SettingPreview() {
        CamerMeteoTheme {
            MyContent()
        }
    }

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun SettingDarkPreview() {
        CamerMeteoTheme {
            MyContent()
        }
    }

}