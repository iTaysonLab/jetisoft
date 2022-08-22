package bruhcollective.itaysonlab.jetisoft.uikit.page

import android.util.Log
import androidx.compose.runtime.Composable
import bruhcollective.itaysonlab.jetisoft.uikit.vm.PageViewModel

@Composable
fun <T> PageLayout(
    state: PageViewModel.State<T>,
    onReload: () -> Unit,
    successContent: @Composable (data: T) -> Unit
) {
    Log.d("X", "state = $state")
    when (state) {
        is PageViewModel.State.Loading -> FullscreenLoading()
        is PageViewModel.State.Error -> FullscreenError(onReload = onReload, exception = state.exception)
        is PageViewModel.State.Loaded -> successContent(state.data)
    }
}
