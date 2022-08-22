package bruhcollective.itaysonlab.microapp.homescreen.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import bruhcollective.itaysonlab.jetisoft.microapp.news.NewsMicroapp
import bruhcollective.itaysonlab.jetisoft.models.news.NewsEntry
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.core.find

@Immutable
internal class OuterNavigation(
    private val controller: NavHostController,
    private val destinations: Destinations
) {
    fun navigateToNewsEntry(
        entry: NewsEntry
    ) {
        controller.navigate(
            destinations
                .find<NewsMicroapp>()
                .article(entry.asBase64())
        )
    }
}

internal val LocalOuterNavigation = staticCompositionLocalOf<OuterNavigation> { error("LocalOuterNavigation for this microapp is not defined!") }