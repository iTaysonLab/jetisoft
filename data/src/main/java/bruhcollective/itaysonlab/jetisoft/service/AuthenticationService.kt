package bruhcollective.itaysonlab.jetisoft.service

import bruhcollective.itaysonlab.jetisoft.UbiConstants
import bruhcollective.itaysonlab.jetisoft.models.auth.Session
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/v3/profiles/sessions")
    @Headers("genomeid: ${UbiConstants.GENOME}", "Ubi-AppId: ${UbiConstants.APP_ID_AUTH}")
    suspend fun createSession(
        @Header("Authorization") authHeader: String,
        @Body payload: Map<String, String>
    ): Session
}