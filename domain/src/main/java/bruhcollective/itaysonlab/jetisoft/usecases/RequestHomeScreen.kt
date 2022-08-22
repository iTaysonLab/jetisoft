package bruhcollective.itaysonlab.jetisoft.usecases

import bruhcollective.itaysonlab.jetisoft.models.news.NewsEntry
import bruhcollective.itaysonlab.jetisoft.repository.FeedRepository
import bruhcollective.itaysonlab.jetisoft.repository.FriendsRepository
import bruhcollective.itaysonlab.jetisoft.repository.NewsRepository
import bruhcollective.itaysonlab.jetisoft.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ubi.FeedQuery
import ubi.GetFriendsQuery
import ubi.LastPlayedGameQuery
import javax.inject.Inject

class RequestHomeScreen @Inject constructor(
    private val newsRepository: NewsRepository,
    private val friendsRepository: FriendsRepository,
    private val userRepository: UserRepository,
    private val feedRepository: FeedRepository
) {
    suspend operator fun invoke(): HomeScreen {
        return coroutineScope {
            val friendsDefer = async { friendsRepository.getFriends() }
            val feedDefer = async { feedRepository.getFeed() }
            val lastPlayedDefer = async { userRepository.getLastPlayed() }

            val friends = friendsDefer.await().dataAssertNoErrors.viewer.friends?.nodes ?: emptyList()
            val lastPlayedData = lastPlayedDefer.await()
            val feed = feedDefer.await().dataAssertNoErrors.viewer.feed

            return@coroutineScope HomeScreen(
                news = newsRepository.getNews(),
                friends = friends,
                feed = feed,
                currentlyPlaying = lastPlayedData.dataAssertNoErrors.viewer.currentOnlineGame,
                lastPlayed = lastPlayedData.dataAssertNoErrors.viewer.lastPlayedGame,
            )
        }
    }

    class HomeScreen(
        val news: List<NewsEntry>,
        val friends: List<GetFriendsQuery.Node>,
        val feed: List<FeedQuery.Feed>,
        val currentlyPlaying: LastPlayedGameQuery.CurrentOnlineGame?,
        val lastPlayed: LastPlayedGameQuery.LastPlayedGame?,
    )
}