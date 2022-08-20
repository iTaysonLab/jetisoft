package bruhcollective.itaysonlab.jetisoft.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import bruhcollective.itaysonlab.jetisoft.R
import javax.annotation.concurrent.Immutable

@Immutable
enum class Screen(
    val route: String,
    @StringRes val title: Int = 0,
) {
    // internal
    NavGraph("nav_graph"),
    CoreLoading("coreLoading"),
    // Landing
    Auth("auth"),
    // Main
    Home(title = R.string.tab_home, route = "home"),
    Games(title = R.string.tab_games, route = "games"),
    Social(title = R.string.tab_social, route = "social"),
    Profile(title = R.string.tab_me, route = "me"),
    // Game
    Game(route = "games/{spaceId}"),
    Article(route = "news/{encodedContent}"),
    Stats(route = "stats/{spaceId}"),
    // Config
    Config(route = "config");

    companion object {
        val hideNavigationBar = setOf(NavGraph.route, CoreLoading.route, Auth.route, Config.route, Article.route, Dialog.AuthDisclaimer.route)
        val showInBottomNavigation = mapOf(
            Home to Icons.Rounded.Home,
            Games to Icons.Rounded.Gamepad,
            Profile to Icons.Rounded.Person,
        )
    }
}

@Immutable
enum class Dialog(
    val route: String
) {
    AuthDisclaimer("disclaimer/auth")
}