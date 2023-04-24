package com.example.auctionapp.data.repository

import android.util.Log
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
    val api: ApiService
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
}