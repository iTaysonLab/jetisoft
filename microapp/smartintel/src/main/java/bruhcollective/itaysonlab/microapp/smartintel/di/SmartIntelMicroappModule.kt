package bruhcollective.itaysonlab.microapp.smartintel.di

import bruhcollective.itaysonlab.microapp.core.MicroappEntry
import bruhcollective.itaysonlab.microapp.core.di.MicroappEntryKey
import bruhcollective.itaysonlab.microapp.smartintel.SmartIntelMicroapp
import bruhcollective.itaysonlab.microapp.smartintel.SmartIntelMicroappImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SmartIntelMicroappModule {
    @Singleton
    @Binds
    @IntoMap
    @MicroappEntryKey(value = SmartIntelMicroapp::class)
    fun smartIntelEntry(entry: SmartIntelMicroappImpl): MicroappEntry
}