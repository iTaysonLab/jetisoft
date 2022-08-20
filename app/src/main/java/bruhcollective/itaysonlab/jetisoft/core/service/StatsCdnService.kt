package bruhcollective.itaysonlab.jetisoft.core.service

import bruhcollective.itaysonlab.jetisoft.core.models.stats.StatsMicroappDefinitionRoot
import retrofit2.http.GET
import retrofit2.http.Path

interface StatsCdnService {
    @GET("/prod/definition/{space}.json")
    suspend fun getStatsLayout(
        @Path("space") spaceId: String
    ): StatsMicroappDefinitionRoot

    @GET("/prod/locales/{space}/locale-{lang}.json")
    suspend fun getStatsLocale(
        @Path("space") spaceId: String,
        @Path("lang") language: String = "en-US",
    ): Map<String, String> // format: "title.X", "legend.X"
}