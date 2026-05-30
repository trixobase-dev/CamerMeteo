package cm.trixobase.library.common.ui.widget

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 30/05/26
 */

@SuppressLint("ComposableNaming")
@Composable
fun MyPermissionNotification(): Boolean {
    val isTiramisu = Utils.phone.isTiramisu()
    val context = LocalContext.current.applicationContext
    var permissionGranted: Boolean by remember {
        if (isTiramisu) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (isTiramisu)
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    return permissionGranted
}