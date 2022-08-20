package bruhcollective.itaysonlab.jetisoft.core.models.stats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class StatsCardsContainer(
    @Json(name = "Statscards") val statsCards: List<StatsCard>
)

@JsonClass(generateAdapter = true)
data class StatsCard(
    val statName: String,
    val displayName: String,
    val locale: String,
    val value: String,
    val ordinal: Int,
    val format: String,
    val semantic: String,
    val sort: String,
    val startDate: String,
    val lastModified: String,
    val unit: String,
    val obj: String
)