package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.ext.execute
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import ubi.FeedQuery
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getFeed() = apolloClient execute FeedQuery(platforms = Optional.Present(listOf("android")))
}