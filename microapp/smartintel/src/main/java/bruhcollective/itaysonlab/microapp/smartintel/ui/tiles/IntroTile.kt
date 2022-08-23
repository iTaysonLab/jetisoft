package bruhcollective.itaysonlab.microapp.smartintel.ui.tiles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.microapp.smartintel.R
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import coil.compose.AsyncImage

@Composable
internal fun IntroTile(
    node: SmartIntelNode.Intro
) {
    val surfaceColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = node.background,
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
                                1f to surfaceColor,
                            )
                        )
                    )
                },
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.TipsAndUpdates, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = stringResource(id = R.string.smart_intel_report), fontSize = 13.sp)
                    Text(text = node.formattedDate, fontSize = 13.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(36.dp))
            
            Text(text = node.message)
        }
    }
}