package bruhcollective.itaysonlab.jetisoft.microapp.news

import bruhcollective.itaysonlab.microapp.core.NestedMicroappEntry

abstract class NewsMicroapp: NestedMicroappEntry {
    override val microappRoute = "news"
    override val fullscreenRoutes = listOf(NewsMicroappImpl.InternalRoutes.ArticleView)
    fun article(encoded: String) = "article/$encoded"
}