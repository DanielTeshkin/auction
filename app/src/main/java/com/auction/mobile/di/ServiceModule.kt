package com.auction.mobile.di

import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.data.networking.RefreshApiService
import com.auction.mobile.data.networking.TokenAuthenticator
import com.auction.mobile.data.networking.TokenInterceptor
import com.auction.mobile.tools.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RefreshTokenClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApiTokenClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RefreshTokenRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApiRetrofit

    @Provides
    @RefreshTokenClient
    @Singleton
    fun refreshClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Provides
    @RefreshTokenRetrofit
    @Singleton
    fun providesRetrofitForRefresh(@RefreshTokenClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://auction-project.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @ApiTokenClient
    @Singleton
    fun provideOkHttpClient(api: RefreshApiService, helper: PreferencesHelper): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(TokenInterceptor(helper))
            .addInterceptor(
                TokenAuthenticator(api, helper)
            )
            .build()
    }

    @Provides
    @ApiRetrofit
    @Singleton
    fun providesRetrofit(@ApiTokenClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://auction-project.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun providesApi(@ApiRetrofit retrofit: Retrofit): ApiService = retrofit.create()

    @Provides
    @Singleton
    fun provideRefreshApi(@RefreshTokenRetrofit retrofit: Retrofit): RefreshApiService = retrofit.create()
}