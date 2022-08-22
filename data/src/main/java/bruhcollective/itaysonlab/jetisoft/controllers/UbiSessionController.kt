package bruhcollective.itaysonlab.jetisoft.controllers

import bruhcollective.itaysonlab.jetisoft.models.auth.Session
import bruhcollective.itaysonlab.jetisoft.service.AuthenticationService
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import okhttp3.Request
import okio.ByteString.Companion.encodeUtf8
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UbiSessionController @Inject constructor(
    cfgService: ConfigService,
    private val authService: AuthenticationService
) {
    private var ubiAuthSession by cfgService.jsonCfg<Session>("ubi.session")

    fun enrichRequestWithData(request: Request.Builder, origRequest: Request) {
        ubiAuthSession ?: return // not authorized
        request.header("Authorization", "ubi_v1 t=${ubiAuthSession!!.ticket}")
        request.header("Ubi-SessionId", ubiAuthSession!!.sessionId)
        request.header("Ubi-ProfileId", ubiAuthSession!!.profileId)
    }

    suspend fun isSignedIn(): Boolean {
        val session = ubiAuthSession

        return if (session != null) {
            if (Instant.parse(session.expiration) < Clock.System.now()) {
                val canUseThisSession = session.rememberMeTicket != null && createSession(
                    SessionType.RememberMeTicket(session.rememberMeTicket)
                ) is SessionResult.Success

                if (!canUseThisSession) {
                    ubiAuthSession = null
                }

                canUseThisSession
            } else {
                true
            }
        } else {
            false
        }
    }

    suspend fun createSession(method: SessionType): SessionResult {
        return try {
            SessionResult.Success(
                authService.createSession(
                    authHeader = method.formatHeader(),
                    payload = method.formatPayload()
                ).also { ubiAuthSession = it })
        } catch (he: HttpException) {
            SessionResult.InvalidInfo
        }
    }

    sealed class SessionResult {
        class Success(val session: Session): SessionResult()
        object InvalidInfo: SessionResult()
    }

    sealed class SessionType {
        abstract fun formatHeader(): String
        open fun formatPayload(): Map<String, String> = emptyMap()

        class EmailPassword(
            private val email: String, private val password: String, private val rememberMe: Boolean = true
        ): SessionType() {
            override fun formatHeader() = "Basic " + "$email:$password".encodeUtf8().base64()
            override fun formatPayload() = mapOf("rememberMe" to rememberMe.toString())
        }

        class RememberMeTicket(
            private val ticket: String, private val rememberMe: Boolean = true
        ): SessionType() {
            override fun formatHeader() = "rm_v1 t=$ticket"
            override fun formatPayload() = mapOf("rememberMe" to rememberMe.toString())
        }
    }

    //

    val profileId get() = ubiAuthSession?.profileId
}