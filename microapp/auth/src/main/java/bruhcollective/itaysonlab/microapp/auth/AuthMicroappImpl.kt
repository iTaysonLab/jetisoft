package bruhcollective.itaysonlab.microapp.auth

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import bruhcollective.itaysonlab.microapp.auth.ui.AuthDisclaimer
import bruhcollective.itaysonlab.microapp.auth.ui.AuthScreen
import bruhcollective.itaysonlab.microapp.auth.ui.LocalOuterNavigation
import bruhcollective.itaysonlab.microapp.auth.ui.OuterNavigation
import bruhcollective.itaysonlab.microapp.core.Destinations
import javax.inject.Inject

class AuthMicroappImpl @Inject constructor(): AuthMicroapp() {
    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: Destinations
    ) {
        navigation(startDestination = microappRoute, route = InternalRoutes.NavGraph) {
            composable(microappRoute) {
                CompositionLocalProvider(LocalOuterNavigation provides OuterNavigation(navController, destinations)) {
                    AuthScreen()
                }
            }

            dialog(InternalRoutes.AuthDisclaimer) {
                AuthDisclaimer(navController)
            }
        }
    }

    internal object InternalRoutes {
        const val NavGraph = "@auth"
        const val AuthDisclaimer = "dialogs/authDisclaimer"
    }
}