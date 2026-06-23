package cm.trixobase.camermeteo.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cm.trixobase.camermeteo.App
import cm.trixobase.camermeteo.AppActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.home.Home
import cm.trixobase.camermeteo.ui.view.home.HomeViewModel
import cm.trixobase.camermeteo.ui.view.region.RegionActivity
import cm.trixobase.camermeteo.ui.view.setting.SettingActivity
import cm.trixobase.library.common.R
import cm.trixobase.library.common.ui.widget.MyDialogExit
import cm.trixobase.library.common.ui.widget.MyLine
import cm.trixobase.library.common.ui.widget.MyPermissionNotification
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : AppActivity() {

    private var viewModel = HomeViewModel()
    private var coroutineScope: CoroutineScope? = null

    private var drawerState: DrawerState? = null
    private var showDialogExit = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class]

        setContent {
            CamerMeteoTheme {
                MyConfig()
                MyDrawer()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyData(this)
    }

    @Composable
    fun MyConfig() {
        val context = LocalContext.current.applicationContext

        // DialogExit
        showDialogExit = remember { mutableStateOf(false) }
        MyDialogExit(
            isShowing = showDialogExit.value,
            onDismiss = { showDialogExit.value = false },
            onConfirm = { Utils.phone.stopApp(context) })
        BackHandler { showDialogExit.value = true }

        // Permission for notification
        MyPermissionNotification()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MyDrawer() {
        val navController = rememberNavController()
        coroutineScope = rememberCoroutineScope()
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        ModalNavigationDrawer(
            drawerState = drawerState!!,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .width(280.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    MyDrawerHead()
                    Spacer(15.dp)
                    MyDrawerBody()

                }
            }) {
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.screen
                ) {
                    composable(Screens.Home.screen) {
                        Home(openDrawer = { coroutineScope?.launch { drawerState?.open() } },
                        viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun MyDrawerHead() {
        val colors = MaterialTheme.colorScheme
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(280.dp)
                .height(120.dp)
                .background(colors.onSurface)
                .padding(10.dp)
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                contentDescription = "CamerMeteo Logo",
                painter = painterResource(id = R.drawable.iv_logo)
            )
            Text(
                text = getString(R.string.app_slogan),
                color = colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    private fun MyDrawerBody() {
        val myTextColor = MaterialTheme.colorScheme.onSurface

        MyItemShare(myTextColor)
        MyItemRate(myTextColor)
        MyItemWrite(myTextColor)

        MyLabel(getString(R.string.menu))

        MyItemCity(myTextColor)
        MyItemSetting(myTextColor)

        MyLabel(getString(R.string.information))

        MyItemPolicy(myTextColor)
        MyItemAbout(myTextColor)
    }

    @Composable
    private fun MyLabel(text: String) {
        val myLabelColor = MaterialTheme.colorScheme.inversePrimary
        Spacer(15.dp)
        MyLine(color = myLabelColor)
        Spacer(10.dp)
        Text(
            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
            text = text,
            color = myLabelColor
        )
    }

    @Composable
    private fun MyItemShare(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.share_app),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    Utils.phone.shareApp(context, R.string.share_app_message)
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemRate(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.rate_us),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Rate",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    Utils.phone.rateApp(context)
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemWrite(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.write_us),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_whatsapp),
                    contentDescription = "Write us",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    Utils.phone.sendMessageWhatsApp(context)
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemCity(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        val localisationIsOn = App.localisationIsOn(context)

        val location = if (!localisationIsOn) {
            val uiState = viewModel.uiState.observeAsState()
            String.format(getString(R.string.my_city), uiState.value!!.city.display.uppercase())
        } else context.getString(R.string.my_position)

        NavigationDrawerItem(
            label = {
                Text(
                    text = location,
                    color = myTextColor,
                    fontSize = (14.5).sp)
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_town),
                    contentDescription = "Rules",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    if (!localisationIsOn)
                        context.startActivity(Intent(context, RegionActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemPolicy(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.confidentials_policies),
                    fontSize = (14.5).sp,
                    color = myTextColor
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_policies),
                    contentDescription = "Policies",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    Utils.phone.openBrowser(context, App.APP_URL_POLICY)
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemSetting(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.settings),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_setting_1),
                    contentDescription = "Settings",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    context.startActivity(Intent(context, SettingActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun MyItemAbout(myTextColor: Color) {
        val context = LocalContext.current.applicationContext
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.about),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "About",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    context.startActivity(Intent(context, AboutActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
                    drawerState?.close()
                }
            })
    }

    @Composable
    private fun Spacer(size: Dp) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(size)
        )
    }

    private fun showLog(method: String, message: String) {
        showLog("MainActivity", method, message)
    }

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "de")
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
            MyDialogExit(true, {}) {}
        }
    }

}

sealed class Screens(val screen: String) {

    data object Home : Screens("Home")

}