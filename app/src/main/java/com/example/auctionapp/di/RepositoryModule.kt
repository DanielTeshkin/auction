package com.example.auctionapp.di

import com.example.auctionapp.data.repository.*
import com.example.auctionapp.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideConfirmNumberRepo(repo: ConfirmNumberRepositoryImpl): ConfirmNumberRepository

    @Binds
    abstract fun provideCodeRepo(repo: CodeRepositoryImpl): CodeRepository

    @Binds
    abstract fun provideLoginRepo(repo: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun provideCompletionRepo(repo: CompletionRepositoryImpl): CompletionRepository

    @Binds
    abstract fun provideProductsRepo(repo: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun provideDetailRepo(repo: DetailInfoRepositoryImpl): DetailInfoRepository


}