package bruhcollective.itaysonlab.jetisoft.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ubi.GetFriendsQuery

@Composable
fun FriendItem(
    node: GetFriendsQuery.Node
) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
    ) {
        Box(
            Modifier
                .height(56.dp + 32.dp)
                .width(300.dp)) {
            AsyncImage(
                model = node.lastPlayedGame?.node?.backgroundUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)))

            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = node.avatarUrl, contentDescription = null, modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .size(48.dp), contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = node.name, maxLines = 1, color = MaterialTheme.colorScheme.onSurface, overflow = TextOverflow.Ellipsis)
                    Text(text = node.lastPlayedGame?.node?.name ?: "", maxLines = 1, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            }
        }
    }
}