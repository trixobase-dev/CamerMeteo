package cm.trixobase.camermeteo.ui.view.drawer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.camermeteo.ui.widget.MyLine

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
            MyLine(color = MaterialTheme.colorScheme.inverseSurface)
            Text("reglesc qdvcq")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RulesPreview() {
    CamerMeteoTheme {
        MyContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RulesDarkPreview() {
    CamerMeteoTheme {
        MyContent()
    }
}

