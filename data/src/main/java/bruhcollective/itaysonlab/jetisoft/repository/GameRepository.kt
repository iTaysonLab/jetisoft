package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.ext.execute
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import ubi.GetGameInfoQuery
import ubi.GetSmartIntelsQuery
import ubi.PeriodicListQuery
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getGameInfo(spaceId: String) = apolloClient execute GetGameInfoQuery(spaceId = spaceId)
    suspend fun getPeriodicChallenges(spaceId: String) = apolloClient execute PeriodicListQuery(spaceId = spaceId)
    suspend fun getSmartIntel(spaceId: String) = apolloClient execute GetSmartIntelsQuery(spaceId = spaceId, limit = Optional.Present(4))
}