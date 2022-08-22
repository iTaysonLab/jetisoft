package bruhcollective.itaysonlab.jetisoft.repository

import bruhcollective.itaysonlab.jetisoft.ext.execute
import com.apollographql.apollo3.ApolloClient
import ubi.GamesQuery
import ubi.LastPlayedGameQuery
import ubi.type.UserGameOrderField
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getLastPlayed() = apolloClient execute LastPlayedGameQuery()

    suspend fun getGameLibrary(sortBy: GameSortField) = apolloClient execute GamesQuery(orderByField = when (sortBy) {
        GameSortField.RecentlyPlayed -> UserGameOrderField.LAST_PLAYED_DATE
        GameSortField.ReleaseDate -> UserGameOrderField.RELEASE_DATE
        GameSortField.Name -> UserGameOrderField.NAME
    })

    enum class GameSortField {
        RecentlyPlayed, ReleaseDate, Name
    }
}