package bruhcollective.itaysonlab.jetisoft.ui.screens.stats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.core.db.UbiUserController
import bruhcollective.itaysonlab.jetisoft.core.math.UbiMath
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsCard
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsMicroappDefTab
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsTabItem
import bruhcollective.itaysonlab.jetisoft.core.service.StatsCdnService
import bruhcollective.itaysonlab.jetisoft.core.service.StatsService
import bruhcollective.itaysonlab.jetisoft.ui.screens.LocalNavigationController
import bruhcollective.itaysonlab.jetisoft.ui.screens.stats.components.StatsTabPage
import bruhcollective.itaysonlab.jetisoft.ui.shared.FullscreenLoading
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun StatsScreen (
    viewModel: StatsScreenViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val navController = LocalNavigationController.current

    when (val state = viewModel.state) {
        StatsScreenViewModel.State.Loading -> FullscreenLoading()
        is StatsScreenViewModel.State.Loaded -> {
            val pages = remember(state.tabs) {
                state.tabs.map { it.name.replaceFirstChar { char -> char.titlecaseChar() } }
            }

            Scaffold(
                topBar = {
                    Row {
                        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(start = 8.dp).size(48.dp)) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                        }

                        ScrollableTabRow(
                            selectedTabIndex = pagerState.currentPage,
                            modifier = Modifier.statusBarsPadding(),
                            divider = {},
                            edgePadding = 8.dp,
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier
                                        .pagerTabIndicatorOffset(
                                            pagerState,
                                            tabPositions
                                        )
                                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                )
                            }
                        ) {
                            pages.forEachIndexed { index, subPage ->
                                Tab(
                                    text = { Text(subPage) },
                                    selected = pagerState.currentPage == index,
                                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                                )
                            }
                        }
                    }
                }, modifier = Modifier.statusBarsPadding()
            ) { padding ->
                HorizontalPager(count = state.tabs.size, modifier = Modifier.padding(padding), state = pagerState) {
                    StatsTabPage(tab = state.tabs[it], localeFetcher = viewModel::localized, statValueFetcher = viewModel::value)
                }
            }
        }
    }
}

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val ubiUserController: UbiUserController,
    private val statsService: StatsService,
    private val statsCdnService: StatsCdnService,
): ViewModel() {
    private var locale: Map<String, String> = emptyMap()
    private var stats: Map<String, StatsCard> = emptyMap()

    var state by mutableStateOf<State>(State.Loading)
    private set

    init {
        viewModelScope.launch {
            load(savedState.get<String>("spaceId")!!)
        }
    }

    private suspend fun load(spaceId: String) {
        locale = statsCdnService.getStatsLocale(spaceId, language = "en-US")
        stats = statsService.getStatsCards(profileId = ubiUserController.profileId!!, spaceId = spaceId).statsCards.associateBy { it.statName }
        state = State.Loaded(
            tabs = statsCdnService.getStatsLayout(spaceId).microapp.navigation.ready,
        )
    }

    sealed class State {
        class Loaded(
            val tabs: List<StatsMicroappDefTab>,
        ): State()

        object Loading: State()
    }

    fun localized(str: String) = locale[str] ?: str
    fun stats(stat: String) = stats[stat] ?: error("No stat named $stat")

    fun value(def: StatsTabItem, str: String): String {
        return when {
            def.formula != null -> UbiMath.format(def.formula) { variable -> stats(variable).value.toDouble() }
            else -> {
                val statCard = stats(str)

                // TODO: format

                statCard.value
            }
        }
    }
}

//


//

@ExperimentalPagerApi
private fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    pageIndexMapping: (Int) -> Int = { it },
): Modifier = layout { measurable, constraints ->
    if (tabPositions.isEmpty()) {
        // If there are no pages, nothing to show
        layout(constraints.maxWidth, 0) {}
    } else {
        val currentPage = minOf(tabPositions.lastIndex, pageIndexMapping(pagerState.currentPage))
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffset
        val indicatorWidth = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.width, nextTab.width, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.width, previousTab.width, -fraction).roundToPx()
        } else {
            currentTab.width.roundToPx()
        }
        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).roundToPx()
        } else {
            currentTab.left.roundToPx()
        }
        val placeable = measurable.measure(
            Constraints(
                minWidth = indicatorWidth,
                maxWidth = indicatorWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )
        layout(constraints.maxWidth, maxOf(placeable.height, constraints.minHeight)) {
            placeable.place(
                indicatorOffset,
                maxOf(constraints.minHeight - placeable.height, 0)
            )
        }
    }
}