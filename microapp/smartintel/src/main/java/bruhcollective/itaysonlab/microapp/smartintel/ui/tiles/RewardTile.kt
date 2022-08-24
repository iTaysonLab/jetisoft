package bruhcollective.itaysonlab.microapp.smartintel.ui.tiles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Toll
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
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import bruhcollective.itaysonlab.microapp.smartintel.R
import coil.compose.AsyncImage

@Composable
internal fun RewardTile(
    node: SmartIntelNode.Reward
) {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = node.reward.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Black.copy(alpha = 0.5f),
                                1f to Color.Black,
                            )
                        )
                    )
                },
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.TopStart)) {
            Icon(Icons.Rounded.Toll, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = stringResource(id = R.string.smart_intel_balance), fontSize = 13.sp)
                Text(text = stringResource(id = R.string.x_units, node.reward.viewer?.node?.unitsBalance ?: 0), fontSize = 13.sp)
            }
        }

        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomStart)) {

            Spacer(modifier = Modifier.height(96.dp))

            Text(
                text = node.reward.name,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(id = R.string.units_with_rarity, node.reward.rarity, node.reward.cost),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}