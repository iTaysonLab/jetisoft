package bruhcollective.itaysonlab.jetisoft.di

import bruhcollective.itaysonlab.jetisoft.UbiConstants
import bruhcollective.itaysonlab.jetisoft.controllers.UbiSessionController
import bruhcollective.itaysonlab.jetisoft.ext.interceptRequest
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    @Named("ubiOkhttp")
    fun provideOkhttp(
        ubiSessionController: UbiSessionController
    ) = OkHttpClient.Builder().interceptRequest { request ->
        header("Accept-Language", Locale.getDefault().language)
        header("Ubi-RequestedPlatformType", "uplay")
        ubiSessionController.enrichRequestWithData(this, request)
    }.build()

    @Provides
    @Singleton
    @Named("anonymousOkhttp")
    fun provideAnonymousOkhttp() = OkHttpClient.Builder().interceptRequest { _ ->
        header("Accept-Language", Locale.getDefault().language)
        header("Ubi-RequestedPlatformType", "uplay")
    }.build()

    @Provides
    @Singleton
    @Named("ubiRetrofit")
    fun provideRetrofit(
        moshi: Moshi,
        @Named("ubiOkhttp") okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .baseUrl(UbiConstants.SERVICES_URL)
        .build()

    @Provides
    @Singleton
    @Named("anonymousRetrofit")
    fun provideAnonymousRetrofit(
        moshi: Moshi,
        @Named("anonymousOkhttp") okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .baseUrl(UbiConstants.SERVICES_URL)
        .build()

    @Provides
    @Singleton
    fun provideApolloClient(
        @Named("ubiOkhttp") okHttpClient: OkHttpClient
    ) = ApolloClient.Builder()
        .serverUrl("https://public-ubiservices.ubi.com/v1/profiles/me/uplay/graphql")
        .okHttpClient(okHttpClient)
        .addHttpHeader("Ubi-AppId", UbiConstants.HOME_SCREEN_APP_ID)
        .httpBatching(batchIntervalMillis = 50)
        .build()
}