package bruhcollective.itaysonlab.jetisoft.ui.screens.game.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.R
import bruhcollective.itaysonlab.jetisoft.ui.kit.datacards.ClassicChallengePagerCard
import bruhcollective.itaysonlab.jetisoft.ui.kit.datacards.PeriodicChallengePagerCard
import bruhcollective.itaysonlab.jetisoft.ui.screens.home.components.SectionHeader
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import ubi.PeriodicListQuery

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GamePeriodicChallengesCards (
    data: List<PeriodicListQuery.Challenge>,
) {
    SectionHeader(header = stringResource(id = R.string.periodic_challenges))
    
    HorizontalPager(
        count = data.size,
        contentPadding = PaddingValues(horizontal = 16.dp),
        itemSpacing = 8.dp
    ) { page ->
        val node = data[page].periodicChallengeData

        PeriodicChallengePagerCard(
            icon = node.imageUrl,
            name = node.name,
            type = node.type,
            progress = node.viewer.meta.progressPercentage,
            progressFormatted = node.viewer.meta.formattedProgress,
            totalFormatted = node.formattedValue,
            inProgress = node.viewer.meta.isInProgress
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}