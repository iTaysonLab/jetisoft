package bruhcollective.itaysonlab.microapp.homescreen.di

import bruhcollective.itaysonlab.microapp.core.MicroappEntry
import bruhcollective.itaysonlab.microapp.core.di.MicroappEntryKey
import bruhcollective.itaysonlab.microapp.homescreen.HomescreenMicroapp
import bruhcollective.itaysonlab.microapp.homescreen.HomescreenMicroappImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomescreenMicroappModule {
    @Singleton
    @Binds
    @IntoMap
    @MicroappEntryKey(value = HomescreenMicroapp::class)
    fun homescreenEntry(entry: HomescreenMicroappImpl): MicroappEntry
}