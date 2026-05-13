package cm.trixobase.camermeteo.ui.view.terms

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 29/04/26
 */

private const val URL_TERMS_AND_POLICIES = "https://trixobase.com/apps/CamerMeteo/policies.html"

@Composable
fun Terms(action: () -> Unit) {
    val context = LocalContext.current.applicationContext
    CamerMeteoTheme {
        Scaffold(
            topBar = { MyToolbar(action, context.getString(R.string.confidentials_policies)) },
            content = { MyContent(Modifier.padding(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyToolbar(backToHome: () -> Unit, title: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 5.dp)
                        .clickable(onClick = { backToHome() }),
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Retour"
                )
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White),
    )
}

@SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
@Composable
private fun MyContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current.applicationContext
    Column(modifier = modifier.fillMaxWidth()) {
        MyLine()
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val webView = WebView(context)
            webView.settings.javaScriptEnabled = true
            webView.settings.builtInZoomControls = true
            webView.settings.displayZoomControls = true
            webView.webChromeClient = WebChromeClient()

            webView.addJavascriptInterface(JavascriptInterface(), "Android")
            webView.loadUrl(URL_TERMS_AND_POLICIES)
            //LocalActivity.current?.setContentView(webView)
            //webView.loadUrl("file:///android_asset/app.html")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    CamerMeteoTheme {
        MyContent()
    }
}

