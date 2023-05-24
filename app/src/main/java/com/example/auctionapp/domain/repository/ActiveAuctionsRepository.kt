package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

interface ActiveAuctionsRepository {

    suspend fun getActiveAuctions(): BaseResponse<List<ProductModel>>
}