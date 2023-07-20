package com.auction.mobile.data.repository

import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.ActiveAuctionsRepository
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