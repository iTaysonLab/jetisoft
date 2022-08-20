package bruhcollective.itaysonlab.jetisoft.core.service

import bruhcollective.itaysonlab.jetisoft.core.UbiConstants
import bruhcollective.itaysonlab.jetisoft.core.models.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {
    @GET("/v1/profiles/me/news")
    @Headers("Ubi-AppId: ${UbiConstants.NEWS_APP_ID}", "Ubi-LocaleCode: en-US", "Ubi-Market: US")
    suspend fun getNews(
        @Query("limit") limit: Int = 5,
        @Query("offset") offset: Int = 0,
        @Query("placement") placement: String = "Top news",
        @Query("spaceId") spaceId: String = UbiConstants.NEWS_SPACE_ID,
        @Query("includeBody") includeBody: Boolean = true,
    ): NewsResponse
}