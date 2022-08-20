package bruhcollective.itaysonlab.jetisoft.core.service

import bruhcollective.itaysonlab.jetisoft.core.UbiConstants
import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsCardsContainer
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StatsService {
    @GET("/v1/profiles/{profile}/statscard")
    @Headers("Ubi-AppId: ${UbiConstants.STATS_APP_ID}", "Ubi-LocaleCode: en-US")
    suspend fun getStatsCards(
        @Path("profile") profileId: String,
        @Query("spaceId") spaceId: String,
    ): StatsCardsContainer
}