package bruhcollective.itaysonlab.jetisoft.ui.screens.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.core.ext.awaitOn
import bruhcollective.itaysonlab.jetisoft.ui.screens.LocalNavigationController
import bruhcollective.itaysonlab.jetisoft.ui.screens.library.components.LibraryHeader
import bruhcollective.itaysonlab.jetisoft.ui.screens.library.components.LibraryItem
import bruhcollective.itaysonlab.jetisoft.ui.shared.FullscreenLoading
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ubi.GamesQuery
import ubi.type.UserGameOrderField
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryScreenViewModel = hiltViewModel()
) {
    val navController = LocalNavigationController.current

    when (viewModel.state) {
        LibraryScreenViewModel.State.Loading -> FullscreenLoading()
        is LibraryScreenViewModel.State.Loaded -> {
            Scaffold(
                topBar = {
                    LibraryHeader(
                        currentSortDirection = viewModel.sortDirection,
                        currentSortField = viewModel.sortField,
                        currentPlatformFilter = viewModel.platformFilter,
                        currentPlatformFiltersAvailable = viewModel.platformFiltersAvailable,
                        onSortDirectionChange = {
                            viewModel.changeSortDirection(it)
                        },
                        onSortFieldChange = {
                            viewModel.changeSortField(it)
                        },
                        onPlatformFilterChange = {
                            viewModel.applyPlatformFilter(it)
                        }
                    )
                }, modifier = Modifier.statusBarsPadding()
            ) { padding ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(padding)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.stateList) { node ->
                        LibraryItem(
                            name = node.name,
                            imageUrl = node.lowBoxArtUrl,
                            platform = node.platform.name,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                navController.navigate("games/${node.spaceId}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private var protoList: List<GamesQuery.Node> = emptyList()

    var state by mutableStateOf<State>(State.Loading)
        private set

    var stateList by mutableStateOf<List<GamesQuery.Node>>(emptyList())
        private set

    var sortDirection by mutableStateOf(SortDirection.Desc)
        private set

    var sortField by mutableStateOf(SortField.Recently)
        private set

    var platformFilter by mutableStateOf<String?>(null)
        private set

    var platformFiltersAvailable by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        viewModelScope.launch { load() }
    }

    private suspend fun load() {
        state = State.Loading
        protoList =
            GamesQuery(
                when (sortField) {
                    SortField.Recently -> UserGameOrderField.LAST_PLAYED_DATE
                    SortField.Release -> UserGameOrderField.RELEASE_DATE
                    SortField.Name -> UserGameOrderField.NAME
                }
            ).awaitOn(apolloClient).data?.viewer?.games?.nodes ?: emptyList()
        enrichWithPlatformFilters()
        appendFilterList()
        state = State.Loaded
    }

    private fun enrichWithPlatformFilters() {
        platformFiltersAvailable = if (platformFilter != null) {
            listOf(platformFilter ?: "")
        } else {
            protoList.map {
                it.platform.name
            }.distinct()
        }
    }

    fun changeSortField(field: SortField) {
        if (sortField == field) return
        sortField = field
        viewModelScope.launch { load() }
    }

    fun changeSortDirection(field: SortDirection) {
        if (sortDirection == field) return
        sortDirection = field
        appendFilterList()
    }

    fun applyPlatformFilter(platform: String?) {
        if (platformFilter == platform) return
        platformFilter = platform
        appendFilterList()
        enrichWithPlatformFilters()
    }

    private fun appendFilterList() {
        stateList = protoList
            .let {
                if (platformFilter != null) it.filter { node ->
                    node.platform.name == platformFilter
                } else it
            }
            .let { if (sortDirection == SortDirection.Asc) it.reversed() else it }
    }

    sealed class State {
        object Loaded : State()
        object Loading : State()
    }
}

enum class SortDirection {
    Desc, Asc
}

enum class SortField(
    val stringRes: Int
) {
    Recently(R.string.sort_recently), Release(R.string.sort_release), Name(R.string.sort_name)
}