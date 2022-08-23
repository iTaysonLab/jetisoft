package bruhcollective.itaysonlab.jetisoft.models

import ubi.fragment.SmartIntelClassicChallengeTile
import ubi.fragment.SmartIntelPeriodicChallengeTile
import ubi.fragment.SmartIntelRewardTile

sealed class SmartIntelNode {
    abstract val id: String

    class Intro(
        override val id: String,
        val message: String,
        val background: String?,
        val formattedDate: String
    ): SmartIntelNode()

    class Congrats(
        override val id: String,
        val message: String,
        val imageUrl: String?
    ): SmartIntelNode()

    class Text(
        override val id: String,
        val text: String,
        val isEnd: Boolean
    ): SmartIntelNode()

    class YoutubeVideo(
        override val id: String,
        val videoTitle: String,
        val authorName: String,
        val imageUrl: String?,
        val videoId: String,
        val duration: Int
    ): SmartIntelNode()

    class Reward(
        override val id: String,
        val reward: SmartIntelRewardTile.Reward
    ): SmartIntelNode()

    class ClassicChallenge(
        override val id: String,
        val classicChallenge: SmartIntelClassicChallengeTile.ClassicChallenge
    ): SmartIntelNode()

    class PeriodicChallenge(
        override val id: String,
        val periodicChallenge: SmartIntelPeriodicChallengeTile.PeriodicChallenge
    ): SmartIntelNode()

    class Unknown(
        override val id: String,
        val type: String
    ): SmartIntelNode()
}