package cm.trixobase.camermeteo.ui.widget

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    contentDescription = "Retour"
                )
                Text(title)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White),
    )
}

@Composable
fun MyLine(modifier: Modifier = Modifier, color: Color = Color.LightGray) {
    Spacer(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color)
    )
}

@Composable
fun MyTextError(modifier: Modifier = Modifier, error: String) {
    val color = MaterialTheme.colorScheme
    Text(
        modifier = modifier
            .padding(25.dp)
            .clip(RectangleShape)
            .border(1.dp, color.onError, shape = RoundedCornerShape(4.dp))
            .padding(1.dp)
            .background(color.errorContainer)
            .padding(vertical = 15.dp, horizontal = 20.dp),
        text = error,
        color = color.error,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMyLine() {
    CamerMeteoTheme {
        MyLine(color = Color.Magenta)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMyTextError() {
    CamerMeteoTheme {
        MyTextError(error = "Exemple de error d\'erreur")
    }
}