package bruhcollective.itaysonlab.jetisoft.ui.kit.datacards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.core.util.DateUtils
import bruhcollective.itaysonlab.jetisoft.ui.theme.UbiPositiveOne
import coil.compose.AsyncImage
import ubi.type.PeriodicChallengeType

@Composable
fun PeriodicChallengePagerCard (
    icon: String?,
    name: String,
    type: PeriodicChallengeType,
    progress: Double,
    progressFormatted: String,
    totalFormatted: String,
    inProgress: Boolean
) {
    val completedString = remember(inProgress, progress) {
        when {
            progress == 100.0 -> "Finished"
            inProgress -> "In progress"
            else -> "Not started"
        }.uppercase()
    }

    val completedProgressString = remember(progressFormatted, totalFormatted) {
        "$progressFormatted / $totalFormatted"
    }

    val typeString = remember(type) {
        when (type) {
            PeriodicChallengeType.COMMUNITY -> "Community event"
            PeriodicChallengeType.REGULAR -> "Regular event"
            PeriodicChallengeType.EVENT -> "Event"
            PeriodicChallengeType.UNKNOWN__ -> ""
        }.uppercase()
    }

    val progressBarProgress =
        remember(progress) {
            progress.toFloat() / 100f
        }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surface)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = name, color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = typeString, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    completedString,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    completedProgressString,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = progressBarProgress,
                trackColor = MaterialTheme.colorScheme.surface,
                color = if (progress == 100.0) UbiPositiveOne else MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}