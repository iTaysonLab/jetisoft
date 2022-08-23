package bruhcollective.itaysonlab.jetisoft.usecases

import bruhcollective.itaysonlab.jetisoft.mappers.mapToSmartIntelModels
import bruhcollective.itaysonlab.jetisoft.repository.GameRepository
import javax.inject.Inject

class RequestGameSmartIntel @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(spaceId: String) = gameRepository.getSmartIntel(spaceId).dataAssertNoErrors.viewer.dailyLogins.nodes.mapToSmartIntelModels()
}