package cm.trixobase.camermeteo.ui.widget

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cm.trixobase.library.common.ui.theme.ApplicationTheme
import cm.trixobase.library.common.ui.theme.provider

/*
 * Powered by Trixobase Enterprise on 13/05/26
 */

@Composable
fun MySubTitle(subTitle: String) {
    Text(
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingDarkPreview() {
    ApplicationTheme {
        Surface {
            MySubTitle("Exemple de text")
        }
    }
}