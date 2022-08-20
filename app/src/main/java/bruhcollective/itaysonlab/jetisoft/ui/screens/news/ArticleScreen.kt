package bruhcollective.itaysonlab.jetisoft.ui.screens.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.core.models.news.NewsEntry
import bruhcollective.itaysonlab.jetisoft.ui.kit.BackButton
import bruhcollective.itaysonlab.jetisoft.ui.screens.news.components.ArticleLargeHeader
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.material3.Material3RichText
import com.squareup.moshi.Moshi
import okio.ByteString.Companion.decodeBase64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen (
    encodedContent: String
) {
    val decodedContent = remember(encodedContent) {
        Moshi.Builder().build().adapter(NewsEntry::class.java).fromJson(
            encodedContent.decodeBase64()!!.string(Charsets.UTF_8)
        )!!
    }

    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    Scaffold(topBar = {
        SmallTopAppBar(
            title = {},
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
            ),
            navigationIcon = {
                BackButton()
            }, modifier = Modifier.statusBarsPadding(),
            scrollBehavior = scrollBehavior
        )
    }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) { padding ->
        LazyColumn(contentPadding = WindowInsets.navigationBars.asPaddingValues()) {
            item {
                ArticleLargeHeader(
                    bgUrl = decodedContent.mediaURL,
                    title = decodedContent.title,
                    subtitle = remember(decodedContent.tags) { decodedContent.tags.joinToString() },
                    date = decodedContent.publicationDate
                )
            }

            item {
                Material3RichText(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Markdown(content = decodedContent.body)
                }
            }
        }
    }
}