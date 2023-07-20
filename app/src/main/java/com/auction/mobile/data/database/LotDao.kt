package com.auction.mobile.data.database

import androidx.room.*
import com.auction.mobile.data.entity.LotContract
import com.auction.mobile.data.entity.LotEntity

@Dao
interface LotDao {

    @Query("SELECT * FROM ${LotContract.TABLE_NAME}")
    suspend fun getAllFavorites(): List<LotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(info: LotEntity)

    @Delete
    suspend fun deleteProduct(info: LotEntity)


}