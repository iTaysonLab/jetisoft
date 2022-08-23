package bruhcollective.itaysonlab.microapp.smartintel.ui

import androidx.lifecycle.SavedStateHandle
import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import bruhcollective.itaysonlab.jetisoft.uikit.vm.PageViewModel
import bruhcollective.itaysonlab.jetisoft.usecases.RequestGameSmartIntel
import bruhcollective.itaysonlab.microapp.smartintel.SmartIntelMicroapp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmartIntelScreenViewModel @Inject constructor(
    private val requestGameSmartIntel: RequestGameSmartIntel,
    private val savedState: SavedStateHandle
): PageViewModel<List<SmartIntelNode>>() {
    init { reload() }
    override suspend fun load() = requestGameSmartIntel(savedState.get<String>(SmartIntelMicroapp.ARG_SPACE_ID)!!)
}