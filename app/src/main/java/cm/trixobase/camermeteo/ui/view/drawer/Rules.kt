package cm.trixobase.camermeteo.ui.view.drawer

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.library.common.Tools

/*
 * Powered by Trixobase Enterprise on 29/04/26
 */

@Composable
fun Rules() {
    CamerMeteoTheme {
        MyContent()
    }
}

@Composable
private fun MyContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {

            val context = LocalContext.current.applicationContext
            var isNotificationGranted by remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    Tools.phone.notify(
                        context,
                        "Forte pluie et vents forts",
                        "N\'oublie pas ton parapluie molah",
                        cm.trixobase.camermeteo.R.drawable.iv_logo)
                }) { Text(text = "Note")}
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RulesDarkPreview() {
    CamerMeteoTheme {
        MyContent()
    }
}

