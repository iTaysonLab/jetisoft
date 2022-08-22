package bruhcollective.itaysonlab.microapp.homescreen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.models.news.NewsEntry
import bruhcollective.itaysonlab.microapp.homescreen.ui.LocalOuterNavigation
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsStack(
    items: List<NewsEntry>
) {
    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        HorizontalPager(count = items.size, state = pagerState) { page ->
            NewsItem(items[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .statusBarsPadding(),
            activeColor = Color.White,
            inactiveColor = Color.White.copy(alpha = 0.5f),
        )

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .height(16.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {}
    }
}

@Composable
private fun NewsItem(
    item: NewsEntry
) {
    val tags = remember(item.tags) { item.tags.joinToString { it.uppercase() } }
    val nav = LocalOuterNavigation.current

    Box(Modifier.fillMaxSize()) {
        AsyncImage(
            model = item.mediaURL,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable { nav.navigateToNewsEntry(item) },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black,
                        )
                    )
                )
        )

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(tags, fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
            Text(
                item.title,
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}