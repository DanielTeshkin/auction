package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

interface ProductRepository {

    suspend fun getProducts(q: String, sort: String): BaseResponse<List<ProductModel>>
}