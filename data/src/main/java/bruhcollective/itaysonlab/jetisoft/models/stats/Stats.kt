package bruhcollective.itaysonlab.jetisoft.models.stats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.zacsweers.moshix.adapters.JsonString

@JsonClass(generateAdapter = true)
class StatsMicroappDefinitionRoot(
    @Json(name = "microApp") val microapp: StatsMicroappDefinition
)

@JsonClass(generateAdapter = true)
class StatsMicroappDefinition(
    val name: String,
    val isGameLegacy: Boolean,
    val navigation: StatsMicroappDefTabs
)

@JsonClass(generateAdapter = true)
class StatsMicroappDefTabs(
    val ready: List<StatsMicroappDefTab>
)

@JsonClass(generateAdapter = true)
class StatsMicroappDefTab(
    val order: Int,
    val name: String,
    val items: List<List<StatsTabItem>>
)

@JsonClass(generateAdapter = true)
data class StatsTabItem(
    val index: Int,
    @JsonString val stats: String,
    val type: List<String>?,
    val clampTo: Int?,
    //
    val formulaResult: String?,
    val mainStat: String?,
    val formula: String?
)