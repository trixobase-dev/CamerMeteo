package cm.trixobase.camermeteo.ui.widget

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.ui.theme.CamerMeteoTheme
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 16/04/26
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(onBackPressedDispatcher: OnBackPressedDispatcher, title: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 5.dp)
                        .clickable(onClick = { onBackPressedDispatcher.onBackPressed() }),
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Retour"
                )
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = MaterialTheme.colorScheme.onSurface),
    )
}

@Composable
fun MyLine(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Spacer(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color)
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMyLine() {
    CamerMeteoTheme {
        MyLine(color = Color.Magenta)
    }
}