package bruhcollective.itaysonlab.microapp.homescreen.ui

import bruhcollective.itaysonlab.jetisoft.uikit.vm.PageViewModel
import bruhcollective.itaysonlab.jetisoft.usecases.RequestHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val requestHomeScreen: RequestHomeScreen
): PageViewModel<RequestHomeScreen.HomeScreen>() {
    init {
        reload()
    }

    override suspend fun load() = requestHomeScreen()
}