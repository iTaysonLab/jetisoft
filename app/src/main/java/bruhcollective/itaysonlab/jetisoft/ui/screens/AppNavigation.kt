package bruhcollective.itaysonlab.jetisoft.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.controllers.UbiSessionController
import bruhcollective.itaysonlab.jetisoft.ui.shared.evo.NavigationBar
import bruhcollective.itaysonlab.jetisoft.uikit.page.FullscreenLoading
import bruhcollective.itaysonlab.microapp.auth.AuthMicroapp
import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry
import bruhcollective.itaysonlab.microapp.core.Destinations
import bruhcollective.itaysonlab.microapp.core.NestedMicroappEntry
import bruhcollective.itaysonlab.microapp.core.ext.ROOT_NAV_GRAPH_ID
import bruhcollective.itaysonlab.microapp.core.ext.navigateRoot
import bruhcollective.itaysonlab.microapp.core.find
import bruhcollective.itaysonlab.microapp.homescreen.HomescreenMicroapp
import bruhcollective.itaysonlab.microapp.library.LibraryMicroapp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation (
    navOffset: Dp,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppNavigationViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        if (navController.currentDestination?.route != "coreLoading") return@LaunchedEffect

        val homeScreen = viewModel.destinations.find<HomescreenMicroapp>()
        val authScreen = viewModel.destinations.find<AuthMicroapp>()

        navController.navigateRoot(if (viewModel.isSignedIn()) {
            homeScreen.destination()
        } else {
            authScreen.destination()
        })
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            0,
                            navOffset
                                .toPx()
                                .toInt()
                        )
                    }
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
            ) {
                viewModel.bottomNavDestinations.forEach { dest ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                dest.second.second,
                                contentDescription = stringResource(dest.second.first)
                            )
                        },
                        label = { Text(stringResource(dest.second.first)) },
                        selected = navController.backQueue.any {
                            it.destination.route?.startsWith(
                                dest.first
                            ) == true
                        },
                        onClick = {
                            navController.navigate(dest.first) {
                                popUpTo(ROOT_NAV_GRAPH_ID) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "coreLoading",
            route = ROOT_NAV_GRAPH_ID,
            modifier = modifier
        ) {
            composable("coreLoading") {
                FullscreenLoading()
            }

            viewModel.destinations.forEach { (key, value) ->
                when (value) {
                    is ComposableMicroappEntry -> with(value) { composable(navController, viewModel.destinations) }
                    is NestedMicroappEntry -> with(value) { navigation(navController, viewModel.destinations) }
                }
            }
        }
    }
}

@HiltViewModel
@JvmSuppressWildcards
class AppNavigationViewModel @Inject constructor(
    private val ubiSessionController: UbiSessionController,
    val destinations: Destinations
): ViewModel() {
    suspend fun isSignedIn() = ubiSessionController.isSignedIn()

    val bottomNavDestinations = listOf(
        destinations.find<HomescreenMicroapp>().destination() to ( R.string.tab_home to Icons.Rounded.Home ),
        destinations.find<LibraryMicroapp>().destination() to ( R.string.tab_games to Icons.Rounded.Gamepad ),
    )
}