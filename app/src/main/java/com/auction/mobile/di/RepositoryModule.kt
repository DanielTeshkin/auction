package com.auction.mobile.di

import com.auction.mobile.data.repository.*
import com.auction.mobile.domain.repository.*
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
    abstract fun provideWonAuctionsRepository(repo: WonAuctionsRepositoryImpl): WonAuctionsRepository
    @Binds
    abstract fun provideActiveAuctionsRepository(repo: ActiveAuctionsRepositoryImpl): ActiveAuctionsRepository

    @Binds
    abstract fun provideMyApplicationRepository(repo: MyApplicationsRepositoryImpl): MyApplicationsRepository

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

    @Binds
    abstract fun provideProfileRepo(repo: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideFavoriteRepo(repo: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    abstract fun provideMainRepo(repo: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun providePassRecoveryRepo(repo: RecoveryPasswordRepositoryImpl): RecoveryPasswordRepository


}