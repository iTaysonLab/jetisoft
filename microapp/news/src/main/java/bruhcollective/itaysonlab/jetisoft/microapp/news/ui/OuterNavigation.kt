package bruhcollective.itaysonlab.jetisoft.microapp.news.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.microapp.core.Destinations

@Immutable
internal class OuterNavigation(
    private val controller: NavHostController,
    private val destinations: Destinations
) {

}

internal val LocalOuterNavigation = staticCompositionLocalOf<OuterNavigation> { error("LocalOuterNavigation for this microapp is not defined!") }