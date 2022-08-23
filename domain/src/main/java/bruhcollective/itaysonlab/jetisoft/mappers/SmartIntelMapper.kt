package bruhcollective.itaysonlab.jetisoft.mappers

import bruhcollective.itaysonlab.jetisoft.models.SmartIntelNode
import bruhcollective.itaysonlab.jetisoft.utils.DateUtils
import ubi.GetSmartIntelsQuery
import ubi.type.DailyLoginSection
import ubi.type.DailyLoginType
import java.util.UUID

fun List<GetSmartIntelsQuery.Node>.mapToSmartIntelModels() = map { node ->
    node.smartIntel.tiles.mapNotNull { tile ->

        when (tile.tileType) {
            DailyLoginType.INTRO_TILE -> {
                tile.smartIntelIntroTile?.let { sTile ->
                    SmartIntelNode.Intro(
                        id = sTile.id,
                        message = sTile.message,
                        background = sTile.game.backgroundUrl,
                        formattedDate = DateUtils.formatDateTimeToLocale(node.smartIntel.date)
                    )
                }
            }

            DailyLoginType.TEXT_TILE -> {
                tile.smartIntelTextTile?.let { sTile ->
                    SmartIntelNode.Text(
                        id = sTile.id,
                        text = sTile.text,
                        isEnd = tile.section == DailyLoginSection.End
                    )
                }
            }

            DailyLoginType.CONGRATS_TILE -> {
                tile.smartIntelCongratsTile?.let { sTile ->
                    SmartIntelNode.Congrats(
                        id = sTile.id,
                        message = sTile.message,
                        imageUrl = sTile.imageUrl
                    )
                }
            }

            DailyLoginType.REWARD_TILE -> {
                tile.smartIntelRewardTile?.let { sTile ->
                    SmartIntelNode.Reward(
                        id = sTile.rewardId,
                        reward = sTile.reward
                    )
                }
            }

            DailyLoginType.CLASSIC_CHALLENGE_TILE -> {
                tile.smartIntelClassicChallengeTile?.let { sTile ->
                    SmartIntelNode.ClassicChallenge(
                        id = sTile.classicChallenge.challengeId,
                        classicChallenge = sTile.classicChallenge
                    )
                }
            }

            DailyLoginType.PERIODIC_CHALLENGE_TILE -> {
                tile.smartIntelPeriodicChallengeTile?.let { sTile ->
                    SmartIntelNode.PeriodicChallenge(
                        id = sTile.periodicChallenge.challengeId,
                        periodicChallenge = sTile.periodicChallenge
                    )
                }
            }

            DailyLoginType.YOUTUBE_VIDEO_TILE -> {
                tile.smartIntelYoutubeVideoTile?.let { sTile ->
                    SmartIntelNode.YoutubeVideo(
                        id = sTile.videoId,
                        videoId = sTile.videoId,
                        videoTitle = sTile.videoTitle ?: "",
                        authorName = sTile.authorName ?: "",
                        imageUrl = sTile.imageUrl,
                        duration = sTile.duration ?: 0,
                    )
                }
            }

            else -> {
                SmartIntelNode.Unknown(
                    id = randomUUID(),
                    type = tile.tileType.rawValue
                )
            }
        }

    }
}.flatten().dropLast(1)

private fun randomUUID() = UUID.randomUUID().toString()