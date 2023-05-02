package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toEntity
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.ProductRepository
import com.squareup.moshi.supertypeOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : ProductRepository {

    override suspend fun getProducts(q: String, sort: String): BaseResponse<List<ProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getProduct(q, sort)
                BaseResponse.Success(result.results.toModel())
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun insertInFavorite(item: ProductModel) {
        try {
            db.insertProduct(item.toEntity())
        } catch (e: java.lang.Exception) {
            Log.d("TTT", e.message.toString())
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

    override suspend fun getAllFavorite(): List<ProductModel> {
        return db.getAllFavorites().toModel()
    }
}