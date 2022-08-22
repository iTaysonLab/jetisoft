package bruhcollective.itaysonlab.jetisoft.usecases

import bruhcollective.itaysonlab.jetisoft.repository.UserRepository
import javax.inject.Inject

class RequestGameLibrary @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(sortBy: UserRepository.GameSortField) =
        userRepository.getGameLibrary(sortBy).data?.viewer?.games?.nodes ?: emptyList()
}