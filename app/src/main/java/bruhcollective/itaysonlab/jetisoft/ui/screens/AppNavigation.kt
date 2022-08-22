package bruhcollective.itaysonlab.jetisoft.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.controllers.UbiSessionController
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
    viewModel: AppNavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        if (navController.currentDestination?.route != "coreLoading") return@LaunchedEffect

        val homeScreen = viewModel.destinations.find<HomescreenMicroapp>()
        val authScreen = viewModel.destinations.find<AuthMicroapp>()

        navController.navigateRoot(if (viewModel.isSignedIn()) {
            homeScreen
        } else {
            authScreen
        }.microappRoute)
    }

    val shouldHideNavigationBar = remember(navBackStackEntry) {
        viewModel.fullscreenDestinations.any {
            it == navBackStackEntry?.destination?.route
        }
    }

    val navBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val navOffset by animateDpAsState(if (shouldHideNavigationBar) 80.dp + navBarHeightDp else 0.dp)
    val navOffsetReverse by animateDpAsState(if (!shouldHideNavigationBar) 80.dp + navBarHeightDp else 0.dp)

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
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                    .navigationBarsPadding(),
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
            modifier = Modifier.padding(bottom = navOffsetReverse)
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

    val fullscreenDestinations = destinations
        .map { it.value.fullscreenRoutes }
        .flatten()
        .distinct()

    val bottomNavDestinations = listOf(
        destinations.find<HomescreenMicroapp>().microappRoute to ( R.string.tab_home to Icons.Rounded.Home ),
        destinations.find<LibraryMicroapp>().microappRoute to ( R.string.tab_games to Icons.Rounded.Gamepad ),
    )
}