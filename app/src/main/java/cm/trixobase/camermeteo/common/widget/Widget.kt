@file:Suppress("ModifierParameter")

package cm.trixobase.camermeteo.common.widget

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cm.trixobase.camermeteo.R

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
fun MyLine(
    color: Color = Color.Gray,
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color)
    )
}