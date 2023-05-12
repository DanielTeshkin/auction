package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toEntity
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ElectedProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : FavoriteRepository {

    override suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getFavoriteProducts()
                BaseResponse.Success(result.results.map { it.toModel() })
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }


    override suspend fun deleteProduct(info: ProductModel) {
        withContext(Dispatchers.IO) {
            try {
                db.deleteProduct(info.toEntity())
            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
            }
        }
    }
}