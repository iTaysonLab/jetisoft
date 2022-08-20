package bruhcollective.itaysonlab.jetisoft.core.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UbiUserController @Inject constructor(
    private val ubiSessionController: UbiSessionController
) {
    val profileId get() = ubiSessionController.profileId
}