package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.UbiConstants
import bruhcollective.itaysonlab.jetisoft.service.NewsService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService
) {
    suspend fun getNews(
        limit: Int = 5,
        offset: Int = 0,
        placement: String = "Top news",
        spaceId: String = UbiConstants.NEWS_SPACE_ID,
        includeBody: Boolean = true,
    ) = newsService.getNews(limit, offset, placement, spaceId, includeBody).news
}