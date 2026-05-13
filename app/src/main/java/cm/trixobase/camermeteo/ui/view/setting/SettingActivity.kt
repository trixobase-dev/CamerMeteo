@file:Suppress("AssignedValueIsNeverRead")

package cm.trixobase.camermeteo.ui.view.setting

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import cm.trixobase.camermeteo.domain.AttributeNames
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.ui.theme.ApplicationTheme

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class SettingActivity : ApplicationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                Scaffold(
                    topBar = { MyToolbar(onBackPressedDispatcher, getString(R.string.settings)) },
                    content = {
                        MyContent(
                            Modifier.padding(it),
                            doGetConfigLanguage(),
                            doGetConfigTemperatureUnity(),
                            doGetConfigLocalisation(),
                            doGetConfigNoteSun(),
                            doGetConfigNoteRain(),
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
        configLanguage: String = Language.FRENCH.unit,
        configUnity: String = AttributeNames.TEMPERATURE_UNITY_CELSIUS,
        configLocalisation: Boolean = false,
        configNoteSun: Boolean = true,
        configNoteRain: Boolean = true,
        configDemo: Boolean = true
    ) {
        val scrollState = rememberScrollState()
        Surface(
            modifier = modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                MyLine()
                /*
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    OptionOne(configUnity)
                    MyLine(color = Color.LightGray)
                    OptionTwo(configSong)
                    MyLine(color = Color.LightGray)
                    OptionThree(configRefreshAuto)
                    MyLine(color = Color.LightGray)
                    OptionFour(configDemo)
                    MyLine(color = Color.LightGray)
                }
                */
                MyAppearance(configLanguage, configUnity)
                MyNotification(configNoteSun, configNoteRain)
                MyLocalisation(configLocalisation, configDemo)
            }
        }
    }

    @Composable
   private fun MyAppearance(configLanguage: String, configUnity: String) {
       Column(
           modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 15.dp),
       ) {
           cm.trixobase.camermeteo.ui.widget.MySubTitle(subTitle = getString(R.string.appearance))
           Card(
               modifier = Modifier.padding(top = 8.dp),
               elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
               shape = RoundedCornerShape(10.dp),
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(115.dp)
               ) {
                   MyConfigTemperature(configUnity)
                   MyConfigLanguage(configLanguage)
               }
           }
       }
    }

    @Composable
    private fun MyConfigTemperature(configUnity: String) {
        val colors = MaterialTheme.colorScheme
        var showDialogTemperature by remember { mutableStateOf(false) }
        var myUnity by remember { mutableStateOf(configUnity) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showDialogTemperature = true },
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_temperature),
                        contentDescription = "Thermometer icon")
                }
                Text(
                    //text = getString(R.string.temperature_unity),
                    text = "Unité de température",
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier.clickable{ showDialogTemperature = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = myUnity,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    tint = colors.onSurfaceVariant,
                    contentDescription = "Temperature unit"
                )
            }
        }

        MyDialogTemperature(
            showDialogTemperature,
            configUnity,
            onConfirm = {
                doConfigTemperatureUnity(unity = it)
                myUnity = it
                showDialogTemperature = false
            },
            onDismiss = { showDialogTemperature = false },
            onRequestDismiss = { showDialogTemperature = false }
        )
    }

    @Composable
    private fun MyConfigLanguage(configLanguage: String) {
        val colors = MaterialTheme.colorScheme
        var showDialogLanguage by remember { mutableStateOf(false) }
        var myLanguage by remember { mutableStateOf(configLanguage) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showDialogLanguage = true },
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_web),
                        contentDescription = "Earth icon")
                }
                Text(
                    //text = getString(R.string.language),
                    text = "Langue",
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier.clickable{ showDialogLanguage = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Language.entries.filter { myLanguage == it.unit }[0].language,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    tint = colors.onSurfaceVariant,
                    contentDescription = "Temperature unit"
                )
            }
        }

        MyDialogLanguage(
            showDialogLanguage,
            configLanguage,
            onConfirm = {
                doConfigLanguage(language = it)
                myLanguage = it
                showDialogLanguage = false
            },
            onDismiss = { showDialogLanguage = false },
            onRequestDismiss = { showDialogLanguage = false }
        )
    }

    @Composable
    private fun MyNotification(configNoteSun: Boolean, configNoteRain: Boolean) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 15.dp),
        ) {
            cm.trixobase.camermeteo.ui.widget.MySubTitle(subTitle = "Notifications")
            Card(
                modifier = Modifier.padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                ) {
                    MyConfigNotificationSun(configNoteSun)
                    MyConfigNotificationRain(configNoteRain)
                }
            }
        }
    }

    @Composable
    private fun MyConfigNotificationSun(configNoteSun: Boolean) {
        val noteSunIsOn = remember { mutableStateOf(configNoteSun) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_sun),
                        contentDescription = "Sun icon")
                }
                Text(
                    //text = getString(R.string.warning_sun),
                    text = "Rappel soleil",
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = noteSunIsOn.value,
                    onCheckedChange = {
                        noteSunIsOn.value = it
                        doConfigNotificationSun(it)
                    }
                )
            }
        }
    }

    @Composable
    private fun MyConfigNotificationRain(configNoteRain: Boolean) {
        val noteRainIsOn = remember { mutableStateOf(configNoteRain) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_rain),
                        contentDescription = "Rain icon")
                }
                Text(
                    text = getString(R.string.warning_rain),
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = noteRainIsOn.value,
                    onCheckedChange = {
                        noteRainIsOn.value = it
                        doConfigNotificationRain(it)
                    }
                )
            }
        }
    }

    @Composable
    private fun MyLocalisation(configLocalisation: Boolean, configDemo: Boolean) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 15.dp),
        ) {
            cm.trixobase.camermeteo.ui.widget.MySubTitle(subTitle = "Localisation")
            Card(
                modifier = Modifier.padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                ) {
                    MyConfigLocalisation(configLocalisation)
                    MyConfigDemo(configDemo)
                }
            }
        }
    }
    @Composable
    private fun MyConfigLocalisation(configLocalisation: Boolean) {
        val localisationAutoIsOn = remember { mutableStateOf(configLocalisation) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_map),
                        contentDescription = "Map icon")
                }
                Text(
                    text = getString(R.string.localisation_auto),
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = localisationAutoIsOn.value,
                    onCheckedChange = {
                        localisationAutoIsOn.value = it
                        doConfigLocalisationAuto(it)
                    }
                )
            }
        }
    }
    @Composable
    private fun MyConfigDemo(configDemo: Boolean) {
        val demoIsOn = remember { mutableStateOf(configDemo) }

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_demo),
                        contentDescription = "Sun icon")
                }
                Text(
                    text = "Mode démo",
                    textAlign = TextAlign.Start)
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = demoIsOn.value,
                    onCheckedChange = {
                        demoIsOn.value = it
                        doConfigDemo(it)
                    }
                )
            }
        }
    }

    @Composable
    private fun MyDialogTemperature(
        showDialog: Boolean,
        configUnity: String,
        onRequestDismiss: () -> Unit,
        onConfirm: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        var selectedOption by rememberSaveable { mutableStateOf(configUnity) }
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
                        MyLine(color = Color.LightGray)
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )

                        AppModule.TEMPERATURE.entries.forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(option.name)
                                RadioButton(
                                    selected = option.unity == selectedOption,
                                    onClick = { selectedOption = option.unity })
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )
                        MyLine(color = Color.LightGray)
                    }
                },
                confirmButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { onConfirm(selectedOption) },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text("Ok", color = colors.onPrimary)
                    }
                },
                dismissButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.secondary)
                    ) {
                        Text(text = getString(R.string.cancel), color = colors.onSecondary)
                    }
                }
            )
        }
    }

    @Composable
    private fun MyDialogLanguage(
        showDialog: Boolean,
        configLanguage: String,
        onRequestDismiss: () -> Unit,
        onConfirm: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        var selectedOption by rememberSaveable { mutableStateOf(configLanguage) }
        val colors = MaterialTheme.colorScheme

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { onRequestDismiss() },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = getString(R.string.language),
                        textAlign = TextAlign.Center,
                        color = colors.primary
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        MyLine(color = Color.LightGray)
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )

                        Language.entries.forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(option.language)
                                RadioButton(
                                    selected = option.unit == selectedOption,
                                    onClick = { selectedOption = option.unit })
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxSize()
                        )
                        MyLine(color = Color.LightGray)
                    }
                },
                confirmButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { onConfirm(selectedOption) },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text("Ok", color = colors.onPrimary)
                    }
                },
                dismissButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.secondary)
                    ) {
                        Text(text = getString(R.string.cancel), color = colors.onSecondary)
                    }
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Preview() {
        ApplicationTheme {
            Surface {
                MyNotification(configNoteSun = true, configNoteRain = true)
            }
        }
    }

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun SettingDarkPreview() {
        ApplicationTheme {
            Surface {
                MyAppearance("en", "°C")
            }
        }
    }

}