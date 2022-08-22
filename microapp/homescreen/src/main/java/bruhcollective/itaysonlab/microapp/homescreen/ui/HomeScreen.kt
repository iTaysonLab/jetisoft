package bruhcollective.itaysonlab.microapp.homescreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import bruhcollective.itaysonlab.jetisoft.uikit.SectionHeader
import bruhcollective.itaysonlab.jetisoft.uikit.page.PageLayout
import bruhcollective.itaysonlab.microapp.homescreen.ui.components.FriendItem
import bruhcollective.itaysonlab.microapp.homescreen.ui.components.LastPlayedCard
import bruhcollective.itaysonlab.microapp.homescreen.ui.components.NewsStack

@Composable
fun HomeScreen (
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    PageLayout(state = viewModel.state, onReload = viewModel::reload) { data ->
        LazyColumn {
            item {
                NewsStack(items = data.news)
            }

            item {
                SectionHeader("Friends", noTopPadding = true)
            }

            item {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(data.friends) { friendNode ->
                        FriendItem(node = friendNode)
                    }
                }
            }

            item {
                SectionHeader("Feed")
            }

            if (data.lastPlayed != null) {
                item {
                    SectionHeader("Last played", noShowAll = true)
                }

                item {
                    LastPlayedCard(data.lastPlayed!!)
                }
            }
        }
    }
}