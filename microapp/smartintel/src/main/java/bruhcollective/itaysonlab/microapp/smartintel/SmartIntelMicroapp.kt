package bruhcollective.itaysonlab.microapp.smartintel

import androidx.navigation.NavType
import androidx.navigation.navArgument
import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry

abstract class SmartIntelMicroapp: ComposableMicroappEntry {
    override val microappRoute = "smartIntel/{${ARG_SPACE_ID}}"

    fun gameDestination(spaceId: String) = "smartIntel/${spaceId}"

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