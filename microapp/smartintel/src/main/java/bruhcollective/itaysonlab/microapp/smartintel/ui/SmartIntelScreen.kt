package bruhcollective.itaysonlab.microapp.smartintel.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import bruhcollective.itaysonlab.jetisoft.uikit.page.PageLayout
import bruhcollective.itaysonlab.microapp.smartintel.R
import bruhcollective.itaysonlab.microapp.smartintel.ui.tiles.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartIntelScreen(
    onYoutubeVideoClick: (String) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: SmartIntelScreenViewModel = hiltViewModel()
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    PageLayout(state = viewModel.state, onReload = viewModel::reload) { nodes ->
        Scaffold(topBar = {
            SmallTopAppBar(
                title = {
                        Text(text = stringResource(id = R.string.smart_intel))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }, modifier = Modifier.statusBarsPadding(),
                scrollBehavior = scrollBehavior
            )
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(nodes, key = { it.id }) { node ->
                    when (node) {
                        is SmartIntelNode.Intro -> IntroTile(node)
                        is SmartIntelNode.Congrats -> CongratsTile(node)
                        is SmartIntelNode.Text -> TextTile(node)
                        is SmartIntelNode.YoutubeVideo -> YoutubeVideoTile(onYoutubeVideoClick, node)
                        is SmartIntelNode.ClassicChallenge -> ClassicChallengeTile(node)
                        else -> UnknownTile()
                    }
                }
            }
        }
    }
}