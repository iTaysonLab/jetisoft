package bruhcollective.itaysonlab.jetisoft.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun LibraryItem(
    name: String,
    imageUrl: String?,
    platform: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = name,
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onClick),
            placeholder = ColorPainter(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )

        Text(text = platform,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.8f))
                .padding(8.dp)
                .align(Alignment.BottomStart),
            fontSize = 13.sp
        )
    }
}