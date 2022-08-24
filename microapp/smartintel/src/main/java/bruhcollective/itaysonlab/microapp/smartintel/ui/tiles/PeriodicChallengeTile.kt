package bruhcollective.itaysonlab.microapp.smartintel.ui.tiles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import coil.compose.AsyncImage

@Composable
internal fun PeriodicChallengeTile(
    node: SmartIntelNode.PeriodicChallenge
) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = node.periodicChallenge.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                placeholder = ColorPainter(MaterialTheme.colorScheme.surface)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = node.periodicChallenge.name,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${node.periodicChallenge.xpPrize} XP",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            node.periodicChallenge.description,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 18.sp
        )
    }
}