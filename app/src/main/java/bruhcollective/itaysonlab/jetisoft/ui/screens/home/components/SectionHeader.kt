package bruhcollective.itaysonlab.jetisoft.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader (
    header: String,
    noTopPadding: Boolean = false,
    noShowAll: Boolean = false,
    onShowAllClick: () -> Unit = {},
) {
    if (noShowAll) {
        Text(text = header, fontSize = 21.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(
            top = if (noTopPadding) 0.dp else 8.dp,
            bottom = 8.dp
        ))
    } else {
        Row(Modifier.fillMaxWidth().padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = header, fontSize = 21.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "Show all", color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable(onClick = onShowAllClick).padding(16.dp))
        }
    }
}