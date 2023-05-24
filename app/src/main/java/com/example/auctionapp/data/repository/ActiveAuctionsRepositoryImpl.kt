package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.ActiveAuctionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActiveAuctionsRepositoryImpl @Inject constructor(
    private val api: ApiService
): ActiveAuctionsRepository {
    override suspend fun getActiveAuctions(): BaseResponse<List<ProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = api.getActiveProductList()
                BaseResponse.Success(res.results.toModel())
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}