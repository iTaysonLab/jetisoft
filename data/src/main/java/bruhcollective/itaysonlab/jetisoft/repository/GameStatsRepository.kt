package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.controllers.UbiUserController
import bruhcollective.itaysonlab.jetisoft.service.StatsCdnService
import bruhcollective.itaysonlab.jetisoft.service.StatsService
import javax.inject.Inject

class GameStatsRepository @Inject constructor(
    private val ubiUserController: UbiUserController,
    private val statsService: StatsService,
    private val statsCdnService: StatsCdnService
) {
    suspend fun getStatsCards(
        profileId: String = ubiUserController.profileId!!,
        spaceId: String,
    ) = statsService.getStatsCards(profileId, spaceId)

    suspend fun getStatsLayout(
        spaceId: String
    ) = statsCdnService.getStatsLayout(spaceId)

    suspend fun getStatsLocale(
        spaceId: String,
        language: String = "en-US"
    ) = statsCdnService.getStatsLocale(spaceId, language)
}