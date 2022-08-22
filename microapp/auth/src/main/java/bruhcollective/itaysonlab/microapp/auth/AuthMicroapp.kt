package bruhcollective.itaysonlab.microapp.auth

import bruhcollective.itaysonlab.microapp.core.NestedMicroappEntry

abstract class AuthMicroapp: NestedMicroappEntry {
    override val microappRoute = "auth"
    fun destination() = microappRoute
}