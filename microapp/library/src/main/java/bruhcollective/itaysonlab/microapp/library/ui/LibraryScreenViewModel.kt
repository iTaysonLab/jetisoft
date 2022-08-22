package bruhcollective.itaysonlab.microapp.library.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.repository.UserRepository
import bruhcollective.itaysonlab.jetisoft.usecases.RequestGameLibrary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ubi.GamesQuery
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val requestGameLibrary: RequestGameLibrary
) : ViewModel() {
    private var protoList: List<GamesQuery.Node> = emptyList()

    var state by mutableStateOf<State>(State.Loading)
        private set

    var stateList by mutableStateOf<List<GamesQuery.Node>>(emptyList())
        private set

    var sortDirection by mutableStateOf(SortDirection.Desc)
        private set

    var sortField by mutableStateOf(UserRepository.GameSortField.RecentlyPlayed)
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
        protoList = requestGameLibrary(sortField)
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

    fun changeSortField(field: UserRepository.GameSortField) {
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

    enum class SortDirection {
        Desc, Asc
    }
}