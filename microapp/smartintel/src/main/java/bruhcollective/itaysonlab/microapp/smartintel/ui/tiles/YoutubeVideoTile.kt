package bruhcollective.itaysonlab.microapp.smartintel.ui.tiles

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import coil.compose.AsyncImage

@Composable
internal fun YoutubeVideoTile(
    onClick: (String) -> Unit,
    node: SmartIntelNode.YoutubeVideo,
) {
    val duration = remember(node.duration) {
        DateUtils.formatElapsedTime(node.duration.toLong())
    }

    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                onClick(node.videoId)
            }) {
        AsyncImage(
            model = node.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Black.copy(alpha = 0.5f),
                                //0.5f to surfaceColor.copy(alpha = 0.5f),
                                //0.85f to surfaceColor,
                                1f to Color.Black,
                            )
                        )
                    )
                },
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Row(
            Modifier
                .align(Alignment.TopStart)
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "YouTube", fontSize = 13.sp)
        }

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)) {
            Text(text = node.videoTitle, fontSize = 16.sp, lineHeight = 19.sp)
            Row {
                Text(text = node.authorName, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = duration, fontSize = 13.sp, modifier = Modifier.alpha(0.7f))
            }
        }
    }
}