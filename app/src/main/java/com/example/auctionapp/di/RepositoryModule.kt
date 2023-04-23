package com.example.auctionapp.di

import com.example.auctionapp.data.repository.ConfirmNumberRepositoryImpl
import com.example.auctionapp.domain.repository.ConfirmNumberRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideConfirmNumberRepo(repo: ConfirmNumberRepositoryImpl): ConfirmNumberRepository
}