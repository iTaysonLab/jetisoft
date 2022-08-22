package bruhcollective.itaysonlab.jetisoft.microapp.news.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.microapp.news.ui.components.ArticleLargeHeader
import bruhcollective.itaysonlab.jetisoft.models.news.NewsEntry
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen (
    encodedContent: String,
    onBackPressed: () -> Unit
) {
    val decodedContent = remember(encodedContent) {
        NewsEntry.fromBase64(encodedContent)
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
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
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