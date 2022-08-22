package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.ext.execute
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import ubi.GetGameInfoQuery
import ubi.PeriodicListQuery
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getGameInfo(spaceId: String) = apolloClient execute GetGameInfoQuery(spaceId = Optional.Present(spaceId))
    suspend fun getPeriodicChallenges(spaceId: String) = apolloClient execute PeriodicListQuery(spaceId = spaceId)
}