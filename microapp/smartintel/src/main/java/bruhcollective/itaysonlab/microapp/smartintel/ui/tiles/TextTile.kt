package bruhcollective.itaysonlab.microapp.smartintel.ui.tiles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode

@Composable
internal fun TextTile(
    node: SmartIntelNode.Text
) {
    Text(
        text = node.text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .padding(bottom = if (node.isEnd) 8.dp else 0.dp)
            .clip(shape = if (node.isEnd) RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) else RectangleShape)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
            .padding(16.dp)
    )
}