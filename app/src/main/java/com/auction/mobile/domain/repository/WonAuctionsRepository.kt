package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ProductModel

interface WonAuctionsRepository {

    suspend fun getAllWonProducts(): BaseResponse<List<ProductModel>>
}