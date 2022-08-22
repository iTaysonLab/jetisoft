package bruhcollective.itaysonlab.jetisoft.models.news

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encodeUtf8

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
) {
    companion object {
        fun fromBase64(encodedContent: String) = Moshi.Builder().build().adapter(NewsEntry::class.java).fromJson(encodedContent.decodeBase64()!!.string(Charsets.UTF_8))!!
    }
    fun asBase64() = Moshi.Builder().build().adapter(NewsEntry::class.java).toJson(this).encodeUtf8().base64Url()
}