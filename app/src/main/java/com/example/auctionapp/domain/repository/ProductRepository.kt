package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

interface ProductRepository {

    suspend fun getProducts(): BaseResponse<List<ProductModel>>
}