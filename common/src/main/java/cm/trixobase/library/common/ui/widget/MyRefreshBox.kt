@file: Suppress("unused")

package cm.trixobase.library.common.ui.widget

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 06/05/26
 */

@Composable
fun RefreshBox(
    list: List<String> = listOf(),
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            MyCustomIndicator(
                state = state,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        LazyColumn(Modifier.fillMaxWidth().height(150.dp)) {
            items(list) {
                ListItem({ Text(text = it) })
            }
        }
    }
}

// ...
@Composable
private fun MyCustomIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(top = 15.dp, end = 30.dp).pullToRefresh(
            state = state,
            isRefreshing = isRefreshing,
            threshold = PositionalThreshold,
            onRefresh = {

            }
        ),
        contentAlignment = Alignment.TopEnd
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = 2500),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(Modifier.size(45.dp))
            } else {
                val distanceFraction = { state.distanceFraction.coerceIn(0f, 3f) }
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .size(35.dp)
                        .graphicsLayer {
                            val progress = distanceFraction()
                            this.alpha = progress
                            this.scaleX = progress
                            this.scaleY = progress
                        }
                )
            }
        }
    }
}