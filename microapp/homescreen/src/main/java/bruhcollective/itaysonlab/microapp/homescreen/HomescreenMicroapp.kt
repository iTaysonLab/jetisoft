package bruhcollective.itaysonlab.microapp.homescreen

import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry

abstract class HomescreenMicroapp: ComposableMicroappEntry {
    override val microappRoute = "home"
}