package com.auction.mobile.data.repository

import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.WonAuctionsRepository
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