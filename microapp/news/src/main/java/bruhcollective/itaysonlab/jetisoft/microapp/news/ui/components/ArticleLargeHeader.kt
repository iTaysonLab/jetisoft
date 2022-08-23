package bruhcollective.itaysonlab.jetisoft.microapp.news.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.utils.DateUtils
import coil.compose.AsyncImage

@Composable
fun ArticleLargeHeader(
    bgUrl: String?,
    title: String,
    subtitle: String,
    date: String
) {
    val surfaceColor = MaterialTheme.colorScheme.surface

    val subtitleFormatted = remember(subtitle, date) {
        val formattedDate = DateUtils.formatRelative("${date}Z")

        if (subtitle.isNotEmpty()) {
            "$subtitle • $formattedDate"
        } else {
            formattedDate
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            model = bgUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                //0.5f to surfaceColor.copy(alpha = 0.5f),
                                //0.85f to surfaceColor,
                                1f to surfaceColor,
                            )
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )

            Text(text = subtitleFormatted)
        }
    }
}