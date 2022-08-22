package bruhcollective.itaysonlab.microapp.gamestats

import androidx.navigation.NavType
import androidx.navigation.navArgument
import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry

abstract class GameStatsMicroapp: ComposableMicroappEntry {
    override val microappRoute = "game/{${ARG_SPACE_ID}}/stats"

    fun gameDestination(spaceId: String) = "game/${spaceId}/stats"

    override val arguments = listOf(
        navArgument(ARG_SPACE_ID) {
            type = NavType.StringType
            nullable = false
        }
    )

    internal companion object {
        const val ARG_SPACE_ID = "spaceId"
    }
}