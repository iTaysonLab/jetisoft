package bruhcollective.itaysonlab.microapp.gamepage

import androidx.navigation.NavType
import androidx.navigation.navArgument
import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry

abstract class GamePageMicroapp: ComposableMicroappEntry {
    override val microappRoute = "game/{${ARG_SPACE_ID}}"

    fun gameDestination(spaceId: String) = "game/${spaceId}"

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