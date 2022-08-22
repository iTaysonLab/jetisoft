package bruhcollective.itaysonlab.microapp.auth.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.auth.AuthMicroappImpl
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.core.ext.navigateRoot
import bruhcollective.itaysonlab.microapp.core.find
import bruhcollective.itaysonlab.microapp.homescreen.HomescreenMicroapp

@Immutable
internal class OuterNavigation(
    private val controller: NavHostController,
    private val destinations: Destinations
) {
    fun onSuccess() {
        controller.navigateRoot(
            destinations.find<HomescreenMicroapp>().microappRoute
        )
    }

    fun openAuthDisclaimer() {
        controller.navigate(AuthMicroappImpl.InternalRoutes.AuthDisclaimer)
    }
}

internal val LocalOuterNavigation = staticCompositionLocalOf<OuterNavigation> { error("LocalOuterNavigation for this microapp is not defined!") }