package bruhcollective.itaysonlab.microapp.gamestats

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.gamestats.ui.StatsScreen
import javax.inject.Inject

class GameStatsMicroappImpl @Inject constructor(): GameStatsMicroapp() {
    @Composable
    override fun NavGraphBuilder.Content(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        StatsScreen(onBackPressed = navController::popBackStack)
    }
}