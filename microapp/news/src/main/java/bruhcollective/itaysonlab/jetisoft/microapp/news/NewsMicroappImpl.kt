package bruhcollective.itaysonlab.jetisoft.microapp.news

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import bruhcollective.itaysonlab.jetisoft.microapp.news.ui.ArticleScreen
import bruhcollective.itaysonlab.jetisoft.microapp.news.ui.LocalOuterNavigation
import bruhcollective.itaysonlab.jetisoft.microapp.news.ui.OuterNavigation
import bruhcollective.itaysonlab.microapp.core.Destinations
import javax.inject.Inject

class NewsMicroappImpl @Inject constructor(): NewsMicroapp() {
    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: Destinations
    ) {
        navigation(startDestination = microappRoute, route = InternalRoutes.NavGraph) {
            composable(microappRoute) {
                // TODO
            }

            composable(InternalRoutes.ArticleView) {
                CompositionLocalProvider(LocalOuterNavigation provides OuterNavigation(navController, destinations)) {
                    ArticleScreen(encodedContent = it.arguments!!.getString("content")!!, onBackPressed = navController::popBackStack)
                }
            }
        }
    }

    internal object InternalRoutes {
        const val NavGraph = "@news"
        const val ArticleView = "article/{content}"
    }
}