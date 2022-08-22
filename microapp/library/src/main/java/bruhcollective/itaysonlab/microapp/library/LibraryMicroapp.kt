package bruhcollective.itaysonlab.microapp.library

import bruhcollective.itaysonlab.microapp.core.ComposableMicroappEntry

abstract class LibraryMicroapp: ComposableMicroappEntry {
    override val microappRoute = "library"
}