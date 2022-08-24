package bruhcollective.itaysonlab.microapp.gamepage.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionCard(
    icon: ImageVector,
    title: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth()) {

            Icon(icon, contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = title)

        }
    }
}