package bruhcollective.itaysonlab.jetisoft.ui.screens.stats.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsCard
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsMicroappDefTab
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsTabItem

@Composable
fun StatsTabPage(
    tab: StatsMicroappDefTab,
    localeFetcher: (String) -> String,
    statCardFetcher: (String) -> StatsCard
) {
    val flat = remember(tab) { tab.items.flatten() }

    LazyColumn(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(flat) { item ->
            StatCardRenderer(
                layoutDef = item,
                localeFetcher = localeFetcher,
                statCardFetcher = statCardFetcher,
            )
        }
    }
}

@Composable
private fun StatCardRenderer(
    layoutDef: StatsTabItem,
    localeFetcher: (String) -> String,
    statCardFetcher: (String) -> StatsCard
) {
    val mainStat = remember(layoutDef.stats, layoutDef.formulaResult) {
        layoutDef.formulaResult ?: layoutDef.stats.first()
    }

    val localized = remember(mainStat) {
        localeFetcher("title.$mainStat")
    }

    val stats = remember(mainStat) {
        statCardFetcher(mainStat)
    }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(text = stats.value, color = MaterialTheme.colorScheme.onSurface)
            Text(text = localized, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}