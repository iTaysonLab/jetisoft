package bruhcollective.itaysonlab.jetisoft.usecases

import bruhcollective.itaysonlab.jetisoft.models.stats.StatsCard
import bruhcollective.itaysonlab.jetisoft.models.stats.StatsMicroappDefTab
import bruhcollective.itaysonlab.jetisoft.repository.GameStatsRepository
import javax.inject.Inject

class RequestGameStats @Inject constructor(
    private val gameRepository: GameStatsRepository
) {
    suspend operator fun invoke(spaceId: String): GameStats {
        return GameStats(
            tabs = gameRepository.getStatsLayout(spaceId).microapp.navigation.ready,
            locale = gameRepository.getStatsLocale(spaceId, language = "en-US"),
            statsCards = gameRepository.getStatsCards(spaceId = spaceId).statsCards.associateBy { it.statName }
        )
    }

    class GameStats(
        val tabs: List<StatsMicroappDefTab>,
        val locale: Map<String, String>,
        val statsCards: Map<String, StatsCard>
    )
}