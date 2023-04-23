package com.example.auctionapp.di

import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.data.networking.TokenAuthenticator
import com.example.auctionapp.data.networking.TokenInterceptor
import com.example.auctionapp.tools.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(api: ApiService, helper: PreferencesHelper): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
//            .addInterceptor(
//                TokenAuthenticator(api, helper)
//            )
            .addInterceptor(TokenInterceptor(helper))

            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://auction-project.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiService = retrofit.create()
}