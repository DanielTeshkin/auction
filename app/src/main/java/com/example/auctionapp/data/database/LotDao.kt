package com.example.auctionapp.data.database

import androidx.room.*
import com.example.auctionapp.data.entity.LotContract
import com.example.auctionapp.data.entity.LotEntity
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

@Dao
interface LotDao {

    @Query("SELECT * FROM ${LotContract.TABLE_NAME}")
    suspend fun getAllFavorites(): List<LotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(info: LotEntity)

    @Delete
    suspend fun deleteProduct(info: LotEntity)


}