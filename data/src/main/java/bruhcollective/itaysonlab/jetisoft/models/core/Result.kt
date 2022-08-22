package bruhcollective.itaysonlab.jetisoft.models.core

sealed class Result <T> {
    class Success <T> (val result: T): Result<T>()
    class Error <T> (val error: Exception): Result<T>()
}