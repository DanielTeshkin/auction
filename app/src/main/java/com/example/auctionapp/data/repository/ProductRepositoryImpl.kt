package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toEntity
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.data.model.ProductToSetFavoriteDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ElectedProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.ProductRepository
import com.squareup.moshi.supertypeOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : ProductRepository {

    override suspend fun getProducts(
        q: String,
        sort: String,
        price_min: String,
        price_max: String,
        city: String
    ): BaseResponse<List<ProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getProduct(q, sort, price_min, price_max, city)
                BaseResponse.Success(result.results.toModel())
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun insertInFavorite(item: ProductModel): BaseResponse<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                api.addProductToFavorite(ProductToSetFavoriteDTO(item.id))
                BaseResponse.Success(Unit)
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun deleteProduct(info: ProductModel): BaseResponse<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                api.deleteElectedItem(info.id)
                BaseResponse.Success(Unit)
            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = api.getFavoriteProducts()
                BaseResponse.Success(res.results.map { it.toModel() })
            } catch (e: java.lang.Exception) {
                Log.d("TTT", e.message.toString())
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}
