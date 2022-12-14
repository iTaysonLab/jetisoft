package bruhcollective.itaysonlab.microapp.gamepage.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Redeem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import bruhcollective.itaysonlab.jetisoft.uikit.page.PageLayout
import bruhcollective.itaysonlab.microapp.gamepage.R
import bruhcollective.itaysonlab.microapp.gamepage.ui.components.ActionCard
import bruhcollective.itaysonlab.microapp.gamepage.ui.components.GameClassicChallengesCards
import bruhcollective.itaysonlab.microapp.gamepage.ui.components.GameLargeHeader
import bruhcollective.itaysonlab.microapp.gamepage.ui.components.GamePeriodicChallengesCards

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onBackClicked: () -> Unit,
    onStatsNavigate: (String) -> Unit,
    onSmartIntelNavigate: (String) -> Unit,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    PageLayout(state = viewModel.state, onReload = viewModel::reload) { data ->
        Scaffold(topBar = {
            SmallTopAppBar(
                title = {},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }, modifier = Modifier.statusBarsPadding(),
                scrollBehavior = scrollBehavior
            )
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) { padding ->
            LazyColumn {
                item {
                    GameLargeHeader(
                        bgUrl = data.game.backgroundUrl,
                        logoUrl = data.game.logoColorUrl,
                        name = data.game.name,
                        lastPlayed = data.game.viewer.meta.lastPlayedDate.toString(),
                        platform = data.game.viewer.meta.lastPlayedOn?.name.toString(),
                        fbUrl = data.game.facebookUrl,
                        instUrl = data.game.instagramUrl,
                        websiteUrl = data.game.websiteUrl,
                        redditUrl = data.game.redditUrl,
                        smartIntelSupported = data.game.viewer.meta.isDailyLoginSupported,
                        onSmartIntelNavigate = { onSmartIntelNavigate(data.spaceId) }
                    )
                }

                if (data.game.viewer.meta.classicChallenges.totalCount > 0) {
                    item {
                        GameClassicChallengesCards(
                            data = data.game.viewer.meta.classicChallenges,
                        )
                    }
                }

                if (data.periodic.periodicChallenges.totalCount > 0) {
                    item {
                        GamePeriodicChallengesCards(
                            data = data.periodic.periodicChallenges.challenges,
                        )
                    }
                }

                /*
                item {
                    ActionCard(
                        icon = Icons.Rounded.Redeem,
                        title = stringResource(id = R.string.rewards),
                        modifier = Modifier.fillParentMaxWidth(),
                        onClick = {

                        }
                    )
                }
                 */

                if (data.game.isStatisticsSupported) {
                    item {
                        ActionCard(
                            icon = Icons.Rounded.Analytics,
                            title = stringResource(id = R.string.stats),
                            modifier = Modifier.fillParentMaxWidth().padding(horizontal = 16.dp),
                            onClick = {
                                onStatsNavigate(data.spaceId)
                            }
                        )
                    }
                }
            }
        }
    }
}