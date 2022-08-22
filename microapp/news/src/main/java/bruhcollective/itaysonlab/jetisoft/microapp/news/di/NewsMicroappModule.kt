package bruhcollective.itaysonlab.jetisoft.microapp.news.di

import bruhcollective.itaysonlab.jetisoft.microapp.news.NewsMicroapp
import bruhcollective.itaysonlab.jetisoft.microapp.news.NewsMicroappImpl
import bruhcollective.itaysonlab.microapp.core.MicroappEntry
import bruhcollective.itaysonlab.microapp.core.di.MicroappEntryKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NewsMicroappModule {
    @Singleton
    @Binds
    @IntoMap
    @MicroappEntryKey(value = NewsMicroapp::class)
    fun newsEntry(entry: NewsMicroappImpl): MicroappEntry
}