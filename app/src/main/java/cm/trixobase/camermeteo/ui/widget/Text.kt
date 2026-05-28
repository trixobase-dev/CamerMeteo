package cm.trixobase.camermeteo.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.trixobase.library.common.ui.theme.ApplicationTheme
import cm.trixobase.library.common.ui.theme.provider

/*
 * Powered by Trixobase Enterprise on 13/05/26
 */

@Composable
fun MySubTitle(subTitle: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = subTitle.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.secondaryContainer,
        fontSize = 17.sp,
        fontFamily = FontFamily(
            androidx.compose.ui.text.googlefonts.Font(
                googleFont = GoogleFont("ADLaM Display"),
                fontProvider = provider,
            )),
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    ApplicationTheme {
        Surface {
            MyTextError(error = "Exemple de text")
        }
    }
}