package bruhcollective.itaysonlab.microapp.library.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import bruhcollective.itaysonlab.microapp.library.ui.components.LibraryHeader
import bruhcollective.itaysonlab.microapp.library.ui.components.LibraryItem
import bruhcollective.itaysonlab.jetisoft.uikit.page.FullscreenLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onGameClick: (String) -> Unit,
    viewModel: LibraryScreenViewModel = hiltViewModel()
) {
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
                                onGameClick(node.spaceId)
                            }
                        )
                    }
                }
            }
        }
    }
}