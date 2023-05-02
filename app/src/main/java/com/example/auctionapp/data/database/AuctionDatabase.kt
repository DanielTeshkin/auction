package com.example.auctionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.auctionapp.data.entity.LotEntity

@Database(
    entities = [
        LotEntity::class
    ],
    version = 1
)
abstract class AuctionDatabase : RoomDatabase() {

    abstract fun lotDao(): LotDao


}