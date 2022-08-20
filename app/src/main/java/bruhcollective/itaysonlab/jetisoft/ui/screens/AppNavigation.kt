package bruhcollective.itaysonlab.jetisoft.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.core.db.UbiSessionController
import bruhcollective.itaysonlab.jetisoft.ui.screens.auth.AuthScreen
import bruhcollective.itaysonlab.jetisoft.ui.screens.game.GameScreen
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.HomeScreen
import bruhcollective.itaysonlab.jetisoft.ui.screens.library.LibraryScreen
import bruhcollective.itaysonlab.jetisoft.ui.screens.news.ArticleScreen
import bruhcollective.itaysonlab.jetisoft.ui.screens.stats.StatsScreen
import bruhcollective.itaysonlab.jetisoft.ui.shared.FullscreenLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun AppNavigation (
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppNavigationViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        if (navController.currentDestination?.route != Screen.CoreLoading.route) return@LaunchedEffect

        navController.navigate((if (viewModel.isSignedIn()) Screen.Home else Screen.Auth).route) {
            popUpTo(Screen.NavGraph.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.CoreLoading.route,
        route = Screen.NavGraph.route,
        modifier = modifier
    ) {
        composable(Screen.CoreLoading.route) {
            FullscreenLoading()
        }

        composable(Screen.Auth.route) {
            AuthScreen()
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Games.route) {
            LibraryScreen()
        }

        composable(Screen.Game.route) {
            GameScreen()
        }

        composable(Screen.Article.route) {
            ArticleScreen(encodedContent = it.arguments?.getString("encodedContent", "") ?: "")
        }

        composable(Screen.Stats.route) {
            StatsScreen()
        }

        dialog(Dialog.AuthDisclaimer.route) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { navController.popBackStack() },
                icon = {
                    Icon(Icons.Rounded.Warning, null)
                },
                title = {
                    Text(stringResource(id = R.string.auth_disclaimer))
                },
                text = {
                    Text(stringResource(id = R.string.auth_disclaimer_text))
                },
                confirmButton = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text(stringResource(id = R.string.confirm))
                    }
                })
        }
    }
}

@HiltViewModel
class AppNavigationViewModel @Inject constructor(
    private val ubiSessionController: UbiSessionController
): ViewModel() {
    suspend fun isSignedIn() = ubiSessionController.isSignedIn()
}