package bruhcollective.itaysonlab.jetisoft.ui.screens.game.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.ui.kit.datacards.ClassicChallengeDataCard
import bruhcollective.itaysonlab.jetisoft.ui.kit.datacards.ClassicChallengePagerCard
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.SectionHeader
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import ubi.GetGameInfoQuery

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameClassicChallengesCards (
    data: GetGameInfoQuery.ClassicChallenges,
) {
    SectionHeader(header = stringResource(id = R.string.classic_challenges))
    
    HorizontalPager(
        count = data.nodes.size,
        contentPadding = PaddingValues(horizontal = 16.dp),
        itemSpacing = 8.dp
    ) { page ->
        val node = data.nodes[page].classicChallengeNode

        ClassicChallengePagerCard(
            icon = node.icon,
            name = node.name,
            xp = node.xpPrize,
            targetCompletion = node.targetCompletion,
            currentCompletion = node.viewer?.meta?.currentCompletion,
            formattedTargetCompletion = node.formattedTargetCompletion,
            formattedCurrentCompletion = node.viewer?.meta?.formattedCurrentCompletion,
            completionDate = node.viewer?.meta?.completionDate,
            isCompleted = node.viewer?.meta?.isCompleted ?: false,
        )
    }
    
    Spacer(modifier = Modifier.height(8.dp))
}