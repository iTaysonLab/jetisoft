package bruhcollective.itaysonlab.microapp.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.homescreen.ui.HomeScreen
import bruhcollective.itaysonlab.microapp.homescreen.ui.LocalOuterNavigation
import bruhcollective.itaysonlab.microapp.homescreen.ui.OuterNavigation
import javax.inject.Inject

class HomescreenMicroappImpl @Inject constructor(): HomescreenMicroapp() {
    @Composable
    override fun NavGraphBuilder.Content(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        CompositionLocalProvider(LocalOuterNavigation provides OuterNavigation(navController, destinations)) {
            HomeScreen()
        }
    }
}