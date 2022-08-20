package bruhcollective.itaysonlab.jetisoft.core.models.news

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsResponse(
    val news: List<NewsEntry>
)

@JsonClass(generateAdapter = true)
data class NewsEntry(
    val body: String,
    val mediaURL: String,
    val newsId: String,
    val publicationDate: String,
    val title: String,
    val type: String,
    val summary: String,
    val tags: List<String>,
    val contentType: String // markdown or html
)