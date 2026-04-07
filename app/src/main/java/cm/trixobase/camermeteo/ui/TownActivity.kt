package cm.trixobase.camermeteo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.ui.theme.CamerMétéoTheme

/*
 * Powered by Trixobase Enterprise on 07/04/26
 */

class TownActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerMeteoTheme {
                MyContent()
            }
        }
    }

    @Composable
    private fun MyContent() {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = { MyToolbar("Votre ville") },
                content = { MyBody(Modifier.padding(it)) }
            )
        }
    }

    @Composable
    fun MyBody(modifier: Modifier) {
        Surface(
            modifier = modifier.fillMaxSize(),
            contentColor = Color.White
        ) {
            Column {
                MyLine(Color.White)
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun GreetingPreview() {
        CamerMeteoTheme {
            MyContent()
        }
    }

}