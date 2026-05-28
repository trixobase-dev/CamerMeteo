package cm.trixobase.library.common.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cm.trixobase.library.common.R
import cm.trixobase.library.common.ui.theme.ApplicationTheme

/*
 * Powered by Trixobase Enterprise on 25/05/26
 */

@Composable
fun MyContentError(error: String, onClick: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val typos = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(160.dp)
                .padding(vertical = 30.dp),
            painter = painterResource(id = R.drawable.iv_info),
            contentDescription = "Image info"
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = context.getString(R.string.warning_information_title),
            style = typos.titleSmall
        )
        Text(
            modifier = Modifier.padding(bottom = 25.dp, start = 25.dp, end = 25.dp),
            text = error,
            textAlign = TextAlign.Center,
            style = typos.bodySmall
        )
        Button(
            modifier = Modifier.width(160.dp),
            onClick = { onClick() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.onPrimary,
                contentColor = colors.primary
            )
        ) {
            Text(
                text = context.getString(R.string.retry))
        }
    }

}

@Composable
fun MyTextErrorSimple(error: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth().padding(25.dp),
        text = error,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "it")
@Composable
private fun Preview() {
    ApplicationTheme {
        Surface {
            MyContentError(error = "Exemple d\'erreur.") {}
        }
    }
}
