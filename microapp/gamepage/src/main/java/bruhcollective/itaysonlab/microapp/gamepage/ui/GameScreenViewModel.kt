package bruhcollective.itaysonlab.microapp.gamepage.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import bruhcollective.itaysonlab.jetisoft.uikit.vm.PageViewModel
import bruhcollective.itaysonlab.jetisoft.usecases.RequestGamePage
import bruhcollective.itaysonlab.microapp.gamepage.GamePageMicroapp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@Stable
class GameScreenViewModel @Inject constructor(
    private val requestGamePage: RequestGamePage,
    private val savedState: SavedStateHandle
) : PageViewModel<RequestGamePage.GamePage>() {
    init { reload() }
    override suspend fun load() = requestGamePage(savedState.get<String>(GamePageMicroapp.ARG_SPACE_ID)!!)
}