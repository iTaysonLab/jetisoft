package bruhcollective.itaysonlab.microapp.gamestats.ui

import androidx.lifecycle.SavedStateHandle
import bruhcollective.itaysonlab.jetisoft.models.stats.StatsTabItem
import bruhcollective.itaysonlab.jetisoft.uikit.vm.PageViewModel
import bruhcollective.itaysonlab.jetisoft.usecases.RequestGameStats
import bruhcollective.itaysonlab.microapp.gamestats.GameStatsMicroapp
import bruhcollective.itaysonlab.microapp.gamestats.math.UbiMath
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsScreenViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val requestGameStats: RequestGameStats
): PageViewModel<RequestGameStats.GameStats>() {
    init {
        reload()
    }

    override suspend fun load() = requestGameStats(savedState.get<String>(GameStatsMicroapp.ARG_SPACE_ID)!!)

    private fun stateData() = (state as? State.Loaded<RequestGameStats.GameStats>)?.data

    fun localized(str: String) = stateData()?.locale?.get(str) ?: str
    fun stats(stat: String) = stateData()?.statsCards?.get(stat) ?: error("No stat named $stat")

    fun value(def: StatsTabItem, str: String): String {
        return when {
            def.formula != null -> UbiMath.format(def.formula!!) { variable -> stats(variable).value.toDouble() }
            else -> {
                val statCard = stats(str)

                // TODO: format

                statCard.value
            }
        }
    }
}