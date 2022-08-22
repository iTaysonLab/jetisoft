package bruhcollective.itaysonlab.jetisoft.microapp.news

import bruhcollective.itaysonlab.microapp.core.NestedMicroappEntry

abstract class NewsMicroapp: NestedMicroappEntry {
    override val microappRoute = "news"

    fun article(encoded: String) = "article/$encoded"
}