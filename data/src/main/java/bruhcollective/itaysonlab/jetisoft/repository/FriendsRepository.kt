package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.ext.execute
import com.apollographql.apollo3.ApolloClient
import ubi.GetFriendsQuery
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getFriends() = apolloClient execute GetFriendsQuery()
}