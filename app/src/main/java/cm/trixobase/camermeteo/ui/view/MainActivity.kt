package cm.trixobase.camermeteo.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cm.trixobase.camermeteo.ApplicationActivity
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.view.home.Home
import cm.trixobase.camermeteo.ui.view.home.HomeViewModel
import cm.trixobase.camermeteo.ui.view.region.RegionActivity
import cm.trixobase.camermeteo.ui.view.setting.SettingActivity
import cm.trixobase.camermeteo.ui.view.terms.Terms
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.library.common.R
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
 * Powered by Trixobase Enterprise on 01/04/26
 */

class MainActivity : ApplicationActivity() {

    private var viewModel = HomeViewModel()
    private var coroutineScope: CoroutineScope? = null
    private var drawerState: DrawerState? = null
    private var showDialogExit = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class]
        /*
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location.apply {
                    viewModel.location["city"] = this.city
                    viewModel.location["latitude"] = this.latitude
                    viewModel.location["longitude"] = this.longitude
                }
            }
        */

        setContent {
            CamerMeteoTheme {
                MyPermission()
                MyNavDrawer()
                MyContent()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyData(this)
    }

    @Suppress("UseExpressionBody", "OVERRIDE_DEPRECATION", "deprecation")
    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    override fun onBackPressed() {
        showDialogExit.value = true
    }

    @Suppress("VariableNeverRead", "AssignedValueIsNeverRead")
    @Composable
    fun MyPermission() {
        val context = LocalContext.current.applicationContext
        var isNotificationGranted: Boolean by remember {
            if (Utils.phone.isTiramisu()) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
            } else mutableStateOf(true)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                isNotificationGranted = isGranted
            }
        )

        LaunchedEffect(key1 = Unit) {
            if (Utils.phone.isTiramisu())
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }

    @Composable
    fun MyContent() {
        showDialogExit = remember { mutableStateOf(false) }
        MyDialogExit(showDialogExit.value)
    }

    @Composable
    fun MyDialogExit(isShowing: Boolean) {
        val context = LocalContext.current.applicationContext
        val colors = MaterialTheme.colorScheme
        if (isShowing) {
            AlertDialog(
                onDismissRequest = { showDialogExit.value = false },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                        text = context.getString(R.string.warning_quit_application),
                        textAlign = TextAlign.Start,
                        color = colors.primary
                    )
                },
                confirmButton = {
                    Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                        Button(
                            modifier = Modifier.width(95.dp),
                            onClick = { Utils.phone.stopApp(context) },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = context.getString(R.string.yes), color = colors.onPrimary)
                        }
                    }
                },
                dismissButton = {
                    Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                        Button(
                            modifier = Modifier.width(95.dp),
                            onClick = { showDialogExit.value = false },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.secondary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = context.getString(R.string.no), color = colors.onSecondary)
                        }
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
                    composable(Screens.Home.screen) { Home(openDrawer = { openDrawer() }, viewModel) }
                    composable(Screens.Policies.screen) { Terms(action = { backToHome(navController) })
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
    private fun MyDrawerBody(navController: NavHostController) {
        val context = LocalContext.current.applicationContext
        val myTextColor = MaterialTheme.colorScheme.onSurface

        MyItemShare(context, myTextColor)
        MyItemRate(context, myTextColor)

        MyLabel(getString(R.string.menu))

        MyItemCity(context, myTextColor)
        MyItemSetting(context, myTextColor)

        MyLabel(getString(R.string.information))

        MyItemPolicy(navController, myTextColor)
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
                    Utils.phone.shareText(
                        context, String.format(
                            context.getString(R.string.share_app_message),
                            "https://play.google.com/?id=${context.packageName}/"
                        )
                    )
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
                    Utils.phone.rateApp(context)
                }
            })
    }

    @Composable
    private fun MyItemCity(context: Context, myTextColor: Color) {
        val uiState = viewModel.uiState.observeAsState()
        NavigationDrawerItem(
            label = {
                Text(
                    text = String.format(getString(R.string.my_city), uiState.value!!.city.display.uppercase()),
                    color = myTextColor,
                    fontSize = (14.5).sp
                )
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
                    drawerState?.close()
                    val intent = Intent(context, RegionActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
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

    private fun backToHome(controller: NavHostController) {
        coroutineScope?.launch {
            controller.goTo(Screens.Home.screen)
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

    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "de")
    @Composable
    private fun PreviewDarkTheme() {
        CamerMeteoTheme {
            MyDialogExit(true)
        }
    }

}

sealed class Screens(val screen: String) {

    data object Home : Screens("Home")
    data object Policies : Screens("Policies")

}