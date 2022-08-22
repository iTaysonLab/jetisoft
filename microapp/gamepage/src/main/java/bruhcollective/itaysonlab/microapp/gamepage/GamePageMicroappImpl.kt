package bruhcollective.itaysonlab.microapp.gamepage

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.core.find
import bruhcollective.itaysonlab.microapp.gamepage.ui.GameScreen
import bruhcollective.itaysonlab.microapp.gamestats.GameStatsMicroapp
import javax.inject.Inject

class GamePageMicroappImpl @Inject constructor(): GamePageMicroapp() {
    @Composable
    override fun NavGraphBuilder.Content(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        GameScreen(onBackClicked = navController::popBackStack, onStatsNavigate = { spaceId ->
            navController.navigate(
                destinations
                    .find<GameStatsMicroapp>()
                    .gameDestination(spaceId)
            )
        })
    }
}