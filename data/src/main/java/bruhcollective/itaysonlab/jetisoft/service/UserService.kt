package bruhcollective.itaysonlab.jetisoft.service

import retrofit2.http.GET

interface UserService {
    @GET("/v3/users/me")
    suspend fun getMe()
}