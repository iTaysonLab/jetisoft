package bruhcollective.itaysonlab.microapp.gamestats.ui.components

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bruhcollective.itaysonlab.jetisoft.models.stats.StatsMicroappDefTab
import bruhcollective.itaysonlab.jetisoft.models.stats.StatsTabItem

@Composable
fun StatsTabPage(
    tab: StatsMicroappDefTab,
    localeFetcher: (String) -> String,
    statValueFetcher: (StatsTabItem, String) -> String
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
                statValueFetcher = statValueFetcher,
            )
        }
    }
}

@Composable
private fun StatCardRenderer(
    layoutDef: StatsTabItem,
    localeFetcher: (String) -> String,
    statValueFetcher: (StatsTabItem, String) -> String
) {
    val bgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)

    val mainStat = remember(layoutDef.stats, layoutDef.mainStat, layoutDef.formulaResult) {
        layoutDef.formulaResult ?: layoutDef.mainStat ?: layoutDef.stats.first()
    }

    val localized = remember(mainStat) {
        localeFetcher("title.$mainStat")
    }

    val value = remember(layoutDef, mainStat) {
        statValueFetcher(layoutDef, mainStat)
    }

    val progress = remember(layoutDef, value) {
        if (layoutDef.formula?.contains("precision: 2") == true) {
            value.toFloat()
        } else {
            0f
        }
    }

    Card(Modifier) {
        Column(Modifier.drawBehind {
            drawRect(
                color = bgColor,
                size = size.copy(width = size.width * progress)
            )
        }.padding(16.dp).fillMaxWidth()) {
            Text(text = localized, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text(text = value, color = MaterialTheme.colorScheme.onSurface, fontSize = 18.sp)
        }
    }
}