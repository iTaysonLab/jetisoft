package bruhcollective.itaysonlab.jetisoft.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.core.ext.awaitOn
import bruhcollective.itaysonlab.jetisoft.core.models.news.NewsEntry
import bruhcollective.itaysonlab.jetisoft.core.service.NewsService
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.FriendItem
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.LastPlayedCard
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.NewsStack
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.SectionHeader
import bruhcollective.itaysonlab.jetisoft.ui.shared.FullscreenLoading
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import ubi.FeedQuery
import ubi.GetFriendsQuery
import ubi.LastPlayedGameQuery
import javax.inject.Inject

@Composable
fun HomeScreen (
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    when (val state = viewModel.state) {
        HomeScreenViewModel.State.Loading -> FullscreenLoading()
        is HomeScreenViewModel.State.Loaded -> {
            LazyColumn {
                item { 
                    NewsStack(items = state.news)
                }

                item {
                    SectionHeader("Friends", noTopPadding = true)
                }

                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.friends) { friendNode ->
                            FriendItem(node = friendNode)
                        }
                    }
                }

                item {
                    SectionHeader("Feed")
                }

                if (state.lastPlayed != null) {
                    item {
                        SectionHeader("Last played", noShowAll = true)
                    }

                    item {
                        LastPlayedCard(state.lastPlayed)
                    }
                }
            }
        }
    }
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val apolloClient: ApolloClient,
    private val newsService: NewsService,
): ViewModel() {
    var state by mutableStateOf<State>(State.Loading)

    init {
        viewModelScope.launch { load() }
    }

    suspend fun load() {
        state = coroutineScope {
            val news = newsService.getNews()

            val friendsDefer = async { GetFriendsQuery().awaitOn(apolloClient) }
            val feedDefer = async { FeedQuery(platforms = Optional.Present(listOf("android"))).awaitOn(apolloClient) }
            val lastPlayedDefer = async { LastPlayedGameQuery().awaitOn(apolloClient) }

            State.Loaded(
                news = news.news,
                friends = friendsDefer.await().data?.viewer?.friends?.nodes ?: emptyList(),
                feed = feedDefer.await().data?.viewer?.feed ?: emptyList(),
                lastPlayed = lastPlayedDefer.await().data?.viewer?.lastPlayedGame,
            )
        }
    }

    sealed class State {
        class Loaded(
            val news: List<NewsEntry>,
            val friends: List<GetFriendsQuery.Node>,
            val feed: List<FeedQuery.Feed>,
            val lastPlayed: LastPlayedGameQuery.LastPlayedGame?
        ): State()
        
        object Loading: State()
    }
}