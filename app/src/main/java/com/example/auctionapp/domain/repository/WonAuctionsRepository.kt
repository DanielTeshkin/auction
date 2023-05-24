package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

interface WonAuctionsRepository {

    suspend fun getAllWonProducts(): BaseResponse<List<ProductModel>>
}