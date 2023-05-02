package com.example.auctionapp.di

import android.app.Application
import androidx.room.Room
import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.database.AuctionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDao(app: Application): AuctionDatabase {
        return Room.databaseBuilder(
            app,
            AuctionDatabase::class.java,
            "auction_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesMovieDao(db: AuctionDatabase): LotDao {
        return db.lotDao()
    }

}