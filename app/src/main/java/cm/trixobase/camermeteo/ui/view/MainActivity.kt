package cm.trixobase.camermeteo.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.drawer.Policies
import cm.trixobase.camermeteo.ui.view.drawer.Rules
import cm.trixobase.camermeteo.ui.view.home.Home
import cm.trixobase.camermeteo.ui.view.home.HomeViewModel
import cm.trixobase.camermeteo.ui.view.setting.SettingActivity
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.library.common.R
import cm.trixobase.library.common.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ApplicationActivity() {

    private var viewModel = HomeViewModel()
    private var coroutineScope: CoroutineScope? = null
    private var drawerState: DrawerState? = null
    private var showDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class]

        //if (savedInstanceState != null)

        setContent {
            CamerMeteoTheme {
                MyNavDrawer()
                MyContent()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyData(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putString(AttributeNames.KEY_APP_TOWN, viewModel.uiState.value?.nom)
    }

    @Suppress("UseExpressionBody", "OVERRIDE_DEPRECATION", "deprecation")
    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    override fun onBackPressed() {
        showDialog.value = true
    }

    @Composable
    fun MyContent() {
        showDialog = remember { mutableStateOf(false) }
        MyDialogExit(showDialog.value)
    }

    @Composable
    fun MyDialogExit(isShowing: Boolean) {
        val colors = MaterialTheme.colorScheme
        if (isShowing) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = getString(R.string.warning_quit_application),
                        textAlign = TextAlign.Start,
                        color = colors.primary
                    )
                },
                confirmButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { finish() },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text(getString(R.string.yes), color = colors.onPrimary)
                    }
                },
                dismissButton = {
                    Button(
                        modifier = Modifier.width(95.dp),
                        onClick = { showDialog.value = false },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.secondary)
                    ) {
                        Text(text = getString(R.string.no), color = colors.onSecondary)
                    }
                }
            )
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MyNavDrawer() {
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
                    MyDrawerBody(navController)

                }
            }) {
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.screen
                ) {
                    composable(Screens.Home.screen) { Home(action = { openDrawer() }, viewModel) }
                    composable(Screens.Rules.screen) { Rules() }
                    composable(Screens.Policies.screen) { Policies() }
                }
            }
        }
    }

    @Composable
    private fun MyDrawerHead() {
        val colors = MaterialTheme.colorScheme
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(120.dp)
                .background(colors.onSurface)
        ) {
            Text("")
        }
    }

    @Composable
    private fun MyDrawerBody(navController: NavHostController) {
        val context = LocalContext.current.applicationContext
        val myTextColor = MaterialTheme.colorScheme.onSurface

        MyItemShare(context, myTextColor)
        MyItemRate(context, myTextColor)

        MyLabel(getString(R.string.rules))

        MyItemRule(navController, myTextColor)
        MyItemPolicy(navController, myTextColor)

        MyLabel(getString(R.string.reglages))

        MyItemSetting(context, myTextColor)
        MyItemAbout(context, myTextColor)
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
    private fun MyItemShare(context: Context, myTextColor: Color) {
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
                    drawerState?.close()
                    Tools.phone.shareApp(context)
                }
            })
    }

    @Composable
    private fun MyItemRate(context: Context, myTextColor: Color) {
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
                    drawerState?.close()
                    Tools.phone.rateApp(context)
                }
            })
    }

    @Composable
    private fun MyItemRule(controller: NavHostController, myTextColor: Color) {
        NavigationDrawerItem(
            label = {
                Text(
                    text = getString(R.string.rules_for_use),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_rules),
                    contentDescription = "Rules",
                    tint = myTextColor
                )
            },
            selected = false,
            onClick = {
                coroutineScope?.launch {
                    drawerState?.close()
                    controller.goTo(Screens.Rules.screen)
                }
            })
    }

    @Composable
    private fun MyItemPolicy(controller: NavHostController, myTextColor: Color) {
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
                    drawerState?.close()
                    controller.goTo(Screens.Policies.screen)
                }
            })
    }

    @Composable
    private fun MyItemSetting(context: Context, myTextColor: Color) {
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
                    drawerState?.close()
                    val intent = Intent(context, SettingActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            })
    }

    @Composable
    private fun MyItemAbout(context: Context, myTextColor: Color) {
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
                    drawerState?.close()
                    val intent = Intent(context, AboutActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
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

    private fun openDrawer() {
        coroutineScope?.launch {
            drawerState?.open()
        }
    }

    private fun NavHostController.goTo(route: String) {
        coroutineScope?.launch {
            drawerState?.close()
        }
        this.navigate(route) {
            popUpTo(0)
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
        }
    }

}

sealed class Screens(val screen: String) {

    data object Home : Screens("Home")
    data object Rules : Screens("Rules")
    data object Policies : Screens("Policies")

}