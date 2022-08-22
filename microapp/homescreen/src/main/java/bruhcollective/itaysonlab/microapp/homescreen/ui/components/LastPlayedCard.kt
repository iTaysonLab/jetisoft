package bruhcollective.itaysonlab.microapp.homescreen.ui.components

import android.text.format.DateUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ubi.LastPlayedGameQuery
import java.time.Instant

@Composable
fun LastPlayedCard(
    lastPlayed: LastPlayedGameQuery.LastPlayedGame
) {
    val ago = remember {
        "played ${
            DateUtils.getRelativeTimeSpanString(Instant.parse(lastPlayed.meta.lastPlayedDate!!).toEpochMilli(), System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_ALL).replace("\\.".toRegex(), "")
        } on ${lastPlayed.meta.lastPlayedOn?.type?.name}"
    }

    OutlinedCard(
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            Modifier
                .height(150.dp)
                .fillMaxWidth()) {
            AsyncImage(
                model = lastPlayed.node.backgroundUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)))

            Column(Modifier.padding(16.dp)) {
                Text(text = lastPlayed.node.name, maxLines = 1, color = MaterialTheme.colorScheme.onSurface, overflow = TextOverflow.Ellipsis)
                Text(text = ago, maxLines = 1, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    lastPlayed.node.statistics.nodes.forEach { node ->
                        OutlinedCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Text(text = node.name, maxLines = 1, color = MaterialTheme.colorScheme.onSurface, overflow = TextOverflow.Ellipsis)
                                Text(text = node.viewer?.meta?.formattedScore ?: "", maxLines = 1, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                            }
                        }
                    }
                }
            }
        }
    }
}