package bruhcollective.itaysonlab.jetisoft.ui.screens.game

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.core.ext.awaitOn
import bruhcollective.itaysonlab.jetisoft.ui.kit.BackButton
import bruhcollective.itaysonlab.jetisoft.ui.screens.LocalNavigationController
import bruhcollective.itaysonlab.jetisoft.ui.screens.game.components.GameClassicChallengesCards
import bruhcollective.itaysonlab.jetisoft.ui.screens.game.components.GameLargeHeader
import bruhcollective.itaysonlab.jetisoft.ui.screens.game.components.GamePeriodicChallengesCards
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.SectionHeader
import bruhcollective.itaysonlab.jetisoft.ui.shared.FullscreenLoading
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ubi.GetGameInfoQuery
import ubi.PeriodicListQuery
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val navigation = LocalNavigationController.current

    when (val state = viewModel.state) {
        GameScreenViewModel.State.Loading -> FullscreenLoading()
        is GameScreenViewModel.State.Loaded -> {
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
                LazyColumn {
                    item {
                        GameLargeHeader(
                            bgUrl = state.data.game.backgroundUrl,
                            logoUrl = state.data.game.logoColorUrl,
                            name = state.data.game.name,
                            lastPlayed = state.data.game.viewer.meta.lastPlayedDate.toString(),
                            platform = state.data.game.viewer.meta.lastPlayedOn?.name.toString(),
                            fbUrl = state.data.game.facebookUrl,
                            instUrl = state.data.game.instagramUrl,
                            websiteUrl = state.data.game.websiteUrl,
                            redditUrl = state.data.game.redditUrl,
                            smartIntelSupported = state.data.game.viewer.meta.isDailyLoginSupported,
                        )
                    }

                    if (state.data.game.viewer.meta.classicChallenges.totalCount > 0) {
                        item {
                            GameClassicChallengesCards(
                                data = state.data.game.viewer.meta.classicChallenges,
                            )
                        }
                    }

                    if (state.periodic.game.viewer.meta.periodicChallenges.totalCount > 0) {
                        item {
                            GamePeriodicChallengesCards(
                                data = state.periodic.game.viewer.meta.periodicChallenges.challenges,
                            )
                        }
                    }

                    item {
                        SectionHeader(header = stringResource(id = R.string.rewards))
                    }

                    item {
                        SectionHeader(header = stringResource(id = R.string.stats), onShowAllClick = {
                            navigation.navigate("stats/${state.spaceId}")
                        })
                    }
                }
            }
        }
    }
}

@HiltViewModel
@Stable
class GameScreenViewModel @Inject constructor(
    private val apolloClient: ApolloClient,
    private val savedState: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf<State>(State.Loading)
        private set

    init {
        viewModelScope.launch { load(savedState.get<String>("spaceId")!!) }
    }

    private suspend fun load(spaceId: String) {
        state = coroutineScope {
            val mainData = async { GetGameInfoQuery(spaceId = Optional.presentIfNotNull(spaceId)).awaitOn(apolloClient) }
            val periodicData = async { PeriodicListQuery(spaceId = spaceId).awaitOn(apolloClient) }

            State.Loaded(
                spaceId = spaceId,
                data = mainData.await().dataAssertNoErrors,
                periodic = periodicData.await().dataAssertNoErrors,
            )
        }
    }

    sealed class State {
        class Loaded(
            val spaceId: String,
            val data: GetGameInfoQuery.Data,
            val periodic: PeriodicListQuery.Data
        ) : State()

        object Loading : State()
    }
}