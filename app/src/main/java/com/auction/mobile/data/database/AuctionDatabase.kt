package com.auction.mobile.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.auction.mobile.data.entity.LotEntity

@Database(
    entities = [
        LotEntity::class
    ],
    version = 1
)
abstract class AuctionDatabase : RoomDatabase() {

    abstract fun lotDao(): LotDao


}