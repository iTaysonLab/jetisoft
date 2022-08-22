package bruhcollective.itaysonlab.jetisoft.di

import bruhcollective.itaysonlab.jetisoft.service.AuthenticationService
import bruhcollective.itaysonlab.jetisoft.service.NewsService
import bruhcollective.itaysonlab.jetisoft.service.StatsCdnService
import bruhcollective.itaysonlab.jetisoft.service.StatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideAuthenticationService(
        @Named("anonymousRetrofit") retrofit: Retrofit
    ) = retrofit.create<AuthenticationService>()

    @Provides
    fun provideNewsService(
        @Named("ubiRetrofit") retrofit: Retrofit
    ) = retrofit.create<NewsService>()

    @Provides
    fun provideStatsService(
        @Named("ubiRetrofit") retrofit: Retrofit
    ) = retrofit.create<StatsService>()

    @Provides
    fun provideStatsCdnService(
        @Named("anonymousRetrofit") retrofit: Retrofit
    ) = retrofit.newBuilder().baseUrl("https://stats.cdn.ubisoft.com/").build().create<StatsCdnService>()
}