package bruhcollective.itaysonlab.jetisoft.core.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionPayload(
    val rememberMe: Boolean
)

@JsonClass(generateAdapter = true)
data class Session(
    val expiration: String,
    val nameOnPlatform: String,
    val platformType: String,
    val profileId: String,
    val rememberMeTicket: String?,
    val serverTime: String,
    val sessionId: String,
    val sessionKey: String,
    val spaceId: String,
    val ticket: String,
    val userId: String,
    val twoFactorAuthenticationTicket: String?,
)