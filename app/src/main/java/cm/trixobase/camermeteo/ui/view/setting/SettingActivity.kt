@file:Suppress("AssignedValueIsNeverRead")

package cm.trixobase.camermeteo.ui.view.setting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.camermeteo.ui.widget.MyToolbar
import cm.trixobase.library.common.R
import cm.trixobase.library.common.constants.Language
import cm.trixobase.library.common.constants.Temperature
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
                MyAppearance()
                MyNotification(configNoteSun, configNoteRain)
                MyLocalisation(configLocalisation, configDemo)
            }
        }
    }

    @Composable
    private fun MyAppearance() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
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
                    MyConfigTemperature()
                    MyConfigLanguage()
                }
            }
        }
    }

    @Composable
    private fun MyConfigTemperature() {
        val temperature = Temperature.entries.filter { doGetConfigTemperature() == it.name }[0]
        val colors = MaterialTheme.colorScheme
        var showDialogTemperature by remember { mutableStateOf(false) }
        var myUnity by remember { mutableStateOf(temperature.display) }

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
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_temperature),
                        contentDescription = "Thermometer icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = getString(R.string.temperature_unity),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                modifier = Modifier.clickable { showDialogTemperature = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "°$myUnity",
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
            onConfirm = {
                doConfigTemperature(temperature = it)
                myUnity = it.display
                showDialogTemperature = false
            },
            onDismiss = { showDialogTemperature = false },
            onRequestDismiss = { showDialogTemperature = false }
        )
    }

    @Composable
    private fun MyConfigLanguage() {
        val language = Language.entries.filter { doGetConfigLanguage() == it.name }[0]
        val colors = MaterialTheme.colorScheme
        var showDialogLanguage by remember { mutableStateOf(false) }
        var myLanguage by remember { mutableStateOf(language.display) }

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
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_web),
                        contentDescription = "Earth icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = getString(R.string.language),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                modifier = Modifier.clickable { showDialogLanguage = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = myLanguage,
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
            onConfirm = {
                doConfigLanguage(language = it)
                myLanguage = it.display
                showDialogLanguage = false
            },
            onDismiss = { showDialogLanguage = false },
            onRequestDismiss = { showDialogLanguage = false }
        )
    }

    @Composable
    private fun MyNotification(configNoteSun: Boolean, configNoteRain: Boolean) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
        ) {
            cm.trixobase.camermeteo.ui.widget.MySubTitle(subTitle = getString(R.string.notifications))
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
        val context = LocalContext.current.applicationContext

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
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_sun),
                        contentDescription = "Sun icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = context.getString(R.string.warning_sun),
                    fontFamily = FontFamily(Font(R.font.akt)),
                    textAlign = TextAlign.Start
                )
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
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_rain),
                        contentDescription = "Rain icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = getString(R.string.warning_rain),
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    textAlign = TextAlign.Start
                )
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
        ) {
            cm.trixobase.camermeteo.ui.widget.MySubTitle(subTitle = getString(R.string.location))
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
        val context = LocalContext.current.applicationContext

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
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_map_target),
                        contentDescription = "Map icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = context.getString(R.string.localisation_auto),
                    textAlign = TextAlign.Start
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    enabled = false,
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
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    shape = IconButtonDefaults.outlinedShape,
                    colors = IconButtonDefaults.filledTonalIconButtonColors()
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.iv_icon_demo),
                        contentDescription = "Sun icon"
                    )
                }
                Text(
                    modifier = Modifier.width(170.dp),
                    text = "Mode démo",
                    textAlign = TextAlign.Start
                )
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
        onRequestDismiss: () -> Unit,
        onConfirm: (Temperature) -> Unit,
        onDismiss: () -> Unit
    ) {
        val context = LocalContext.current.applicationContext
        val temperature = Temperature.entries.filter { doGetConfigTemperature() == it.name }[0]
        var selectedOption by rememberSaveable { mutableStateOf(temperature.name) }
        val colors = MaterialTheme.colorScheme

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { onRequestDismiss() },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = context.getString(R.string.temperature_unity),
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

                        Temperature.entries.forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(option.name.lowercase().replaceFirstChar { it.uppercase() })
                                RadioButton(
                                    selected = option.name == selectedOption,
                                    onClick = { selectedOption = option.name })
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
                    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Button(
                            modifier = Modifier.width(120.dp),
                            onClick = {
                                val t = Temperature.entries.filter { selectedOption == it.name }[0]
                                onConfirm(t)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(context.getString(R.string.ok), color = colors.onPrimary)
                        }
                    }
                },
                dismissButton = {
                    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Button(
                            modifier = Modifier.width(120.dp),
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.secondary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = context.getString(R.string.cancel), color = colors.onSecondary)
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun MyDialogLanguage(
        showDialog: Boolean,
        onRequestDismiss: () -> Unit,
        onConfirm: (Language) -> Unit,
        onDismiss: () -> Unit
    ) {
        val context = LocalContext.current.applicationContext
        val language = Language.entries.filter { doGetConfigLanguage() == it.name }[0]
        var selectedOption by rememberSaveable { mutableStateOf(language.name) }
        val colors = MaterialTheme.colorScheme

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { onRequestDismiss() },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = context.getString(R.string.language),
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
                                Text(option.display)
                                RadioButton(
                                    selected = option.name == selectedOption,
                                    onClick = { selectedOption = option.name })
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
                    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Button(
                            modifier = Modifier.width(120.dp),
                            onClick = {
                                val l = Language.entries.filter { selectedOption == it.name }[0]
                                onConfirm(l)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(context.getString(R.string.ok), color = colors.onPrimary)
                        }
                    }
                },
                dismissButton = {
                    Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Button(
                            modifier = Modifier.width(120.dp),
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.secondary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = context.getString(R.string.cancel), color = colors.onSecondary)
                        }
                    }
                }
            )
        }
    }

    @Preview(showBackground = true, locale = "it",
        device = "spec:width=920px,height=1600px,dpi=440"
    )
    @Composable
    private fun Preview() {
        ApplicationTheme {
            Surface {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    MyConfigNotificationSun(true)
                    MyConfigLocalisation(true) }
            }
        }
    }

}