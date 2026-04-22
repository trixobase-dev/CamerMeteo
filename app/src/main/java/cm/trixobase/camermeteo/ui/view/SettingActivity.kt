@file:Suppress("AssignedValueIsNeverRead")

package cm.trixobase.camermeteo.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.AttributesNames
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
                            doGetConfigRefreshAuto()
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun MyContent(
        modifier: Modifier = Modifier,
        configSong: Boolean = true,
        configRefreshAuto: Boolean = true
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
        ) {
            Column {
                MyLine(color = Color.White)
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
                }
            }
        }
    }

    @Composable
    fun OptionOne() {
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
                text = "Unité de température",
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
    fun OptionTwo(configSong: Boolean) {
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
    fun OptionThree(configRefreshAuto: Boolean) {
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
    fun MyDialog(
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
                        //text = getString(R.string.temperature_unity),
                        text = "Unité de température",
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
                            AttributesNames.UNITY_TEMPERATURE_CELSIUS,
                            AttributesNames.UNITY_TEMPERATURE_FAHRENHEIT
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
                        Text("OK", color = colors.onPrimary)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.secondary)
                    ) {
                        Text("Annuler", color = colors.onSecondary)
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

    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun SettingDarkPreview() {
        CamerMeteoTheme {
            MyContent()
        }
    }

}