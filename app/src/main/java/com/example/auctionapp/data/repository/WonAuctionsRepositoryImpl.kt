package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.WonAuctionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WonAuctionsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : WonAuctionsRepository {

    override suspend fun getAllWonProducts(): BaseResponse<List<ProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = api.getWonProductList()
                BaseResponse.Success(res.results.toModel())
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }

        }
    }
}