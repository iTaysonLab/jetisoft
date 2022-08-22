package bruhcollective.itaysonlab.jetisoft.usecases

import bruhcollective.itaysonlab.jetisoft.repository.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ubi.GetGameInfoQuery
import ubi.PeriodicListQuery
import javax.inject.Inject

class RequestGamePage @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(spaceId: String) = coroutineScope {
        val gameInfoDefer = async { gameRepository.getGameInfo(spaceId) }
        val periodicChallengesDefer = async { gameRepository.getPeriodicChallenges(spaceId) }

        return@coroutineScope GamePage(
            spaceId = spaceId,
            game = gameInfoDefer.await().dataAssertNoErrors.game,
            periodic = periodicChallengesDefer.await().dataAssertNoErrors.game.viewer.meta
        )
    }

    class GamePage(
        val spaceId: String,
        val game: GetGameInfoQuery.Game,
        val periodic: PeriodicListQuery.Meta
    )
}