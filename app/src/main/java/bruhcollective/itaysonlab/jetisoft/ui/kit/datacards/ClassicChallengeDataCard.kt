package bruhcollective.itaysonlab.jetisoft.ui.kit.datacards

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.core.util.DateUtils
import bruhcollective.itaysonlab.jetisoft.ui.theme.JetisoftTheme
import bruhcollective.itaysonlab.jetisoft.ui.theme.UbiNegativeOne
import bruhcollective.itaysonlab.jetisoft.ui.theme.UbiPositiveOne
import coil.compose.AsyncImage

@Composable
fun ClassicChallengeDataCard(
    icon: String,
    name: String,
    description: String,
    xp: Int,
    formattedTargetCompletion: String,
    formattedCurrentCompletion: String?,
    completionDate: String?,
    isCompleted: Boolean,
) {
    Card {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(200.dp)
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
                    Text(text = name, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "$xp XP",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = description, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ClassicChallengePagerCard(
    icon: String,
    name: String,
    xp: Int,
    targetCompletion: String,
    currentCompletion: String?,
    formattedTargetCompletion: String,
    formattedCurrentCompletion: String?,
    completionDate: String?,
    isCompleted: Boolean,
) {
    val completedString = remember(completionDate) {
        if (completionDate != null) {
            "Completed"
        } else {
            "Not completed"
        }.uppercase()
    }

    val completedProgressString = remember(completionDate, formattedCurrentCompletion, formattedTargetCompletion) {
        if (completionDate != null) {
            DateUtils.formatDateTimeToLocale(completionDate)
        } else if (formattedCurrentCompletion != null) {
            "$formattedCurrentCompletion / $formattedTargetCompletion"
        } else {
            formattedTargetCompletion
        }
    }

    val progressBarProgress =
        remember(isCompleted, currentCompletion, targetCompletion) {
            if (isCompleted) {
                1f
            } else {
                (currentCompletion?.toFloat() ?: 0f) / targetCompletion.toFloat()
            }
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
                    Text(text = name, color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "$xp XP",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
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
                color = if (isCompleted) UbiPositiveOne else MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "Pager card (partial, not completed)")
@Composable
fun ClassicChallengePagerCard_Preview_NotCompleted_Partial(

) {
    JetisoftTheme {
        ClassicChallengePagerCard(
            icon = "https://ubiservices.cdn.ubi.com/7c193df7-ac6c-48ed-ba63-af6f012880a3/connect/challengeAsset75489.png",
            name = "Junior Treasure Hunter",
            xp = 50,
            targetCompletion = "5",
            currentCompletion = "4",
            formattedTargetCompletion = "5",
            formattedCurrentCompletion = "4",
            completionDate = null,
            isCompleted = false
        )
    }
}

@Preview(name = "Pager card (completed)")
@Composable
fun ClassicChallengePagerCard_Preview_Completed(

) {
    JetisoftTheme {
        ClassicChallengePagerCard(
            icon = "https://ubiservices.cdn.ubi.com/7c193df7-ac6c-48ed-ba63-af6f012880a3/connect/challengeAsset75489.png",
            name = "Junior Treasure Hunter",
            xp = 50,
            targetCompletion = "5",
            currentCompletion = "4",
            formattedTargetCompletion = "5",
            formattedCurrentCompletion = "4",
            completionDate = "2021-10-07T06:08:07.696Z",
            isCompleted = true
        )
    }
}