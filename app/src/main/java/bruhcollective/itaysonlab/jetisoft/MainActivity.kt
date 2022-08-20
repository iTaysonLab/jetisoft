package bruhcollective.itaysonlab.jetisoft

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bruhcollective.itaysonlab.jetisoft.ui.screens.AppNavigation
import bruhcollective.itaysonlab.jetisoft.ui.screens.LocalNavigationController
import bruhcollective.itaysonlab.jetisoft.ui.screens.NavigationController
import bruhcollective.itaysonlab.jetisoft.ui.screens.Screen
import bruhcollective.itaysonlab.jetisoft.ui.theme.JetisoftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetisoftTheme {
                val navController = rememberNavController()
                val lambdaNavController = NavigationController { navController }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val shouldHideNavigationBar = remember(navBackStackEntry) {
                    Screen.hideNavigationBar.any { it == navBackStackEntry?.destination?.route }
                }

                val navBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

                val navOffset by animateDpAsState(if (shouldHideNavigationBar) 80.dp + navBarHeightDp else 0.dp)
                val navOffsetReverse by animateDpAsState(if (!shouldHideNavigationBar) 80.dp + navBarHeightDp else 0.dp)

                CompositionLocalProvider(LocalNavigationController provides lambdaNavController) {
                    Scaffold(
                        bottomBar = {
                            bruhcollective.itaysonlab.jetisoft.ui.shared.evo.NavigationBar(
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
                                Screen.showInBottomNavigation.forEach { (screen, icon) ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                icon,
                                                contentDescription = stringResource(screen.title)
                                            )
                                        },
                                        label = { Text(stringResource(screen.title)) },
                                        selected = lambdaNavController.controller().backQueue.any {
                                            it.destination.route?.startsWith(
                                                screen.route
                                            ) == true
                                        },
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(Screen.NavGraph.route) {
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
                        AppNavigation(navController = navController, modifier = Modifier.padding(bottom = navOffsetReverse))
                    }
                }
            }
        }
    }
}