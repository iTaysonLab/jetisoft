package bruhcollective.itaysonlab.microapp.gamestats.di

import bruhcollective.itaysonlab.microapp.core.MicroappEntry
import bruhcollective.itaysonlab.microapp.core.di.MicroappEntryKey
import bruhcollective.itaysonlab.microapp.gamestats.GameStatsMicroapp
import bruhcollective.itaysonlab.microapp.gamestats.GameStatsMicroappImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GameStatsMicroappModule {
    @Singleton
    @Binds
    @IntoMap
    @MicroappEntryKey(value = GameStatsMicroapp::class)
    fun gameStatsEntry(entry: GameStatsMicroappImpl): MicroappEntry
}